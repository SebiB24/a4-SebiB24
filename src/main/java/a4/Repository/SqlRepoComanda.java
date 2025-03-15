package a4.Repository;

import a4.Domain.Comanda;
import a4.Domain.Produs;
import a4.Domain.ProdusFactory;
import com.github.javafaker.Faker;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SqlRepoComanda extends Repository<Comanda> {
    protected String URL;
    protected Connection conn = null;
    protected ArrayList<Produs> produse;

    public SqlRepoComanda(String URL, ArrayList<Produs> produse) throws RepositoryException{
        this.URL = URL;
        this.produse = produse;
        openConnection();
        createSchema();
        initTables();
        initRepo();
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    protected void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS comenzi(id int, data_livrare varchar(20), lista_produse varchar(20));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }


    protected void initTables() {
        Faker faker = new Faker();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            try (PreparedStatement statement1 = conn.prepareStatement("SELECT COUNT(*) from comenzi")){
                statement1.executeQuery().next();
                if (statement1.executeQuery().getInt(1) == 0)
                    for (int i = 0; i < 50; i++) {
                        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO comenzi VALUES (?, ?, ?)")) {
                            statement.setInt(1, i);
                            Date data = faker.date().past(1000, java.util.concurrent.TimeUnit.DAYS);
                            statement.setString(2, formatter.format(data));
                            String IDs = produse.getFirst().getId() + "," + produse.getLast().getId();
                            statement.setString(3, IDs);
                            statement.executeUpdate();
                        }
                    }
            }
        }catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }

    }

    private void initRepo() throws RepositoryException{
        ArrayList<Comanda> comenzi = getAll();
        for (Comanda comanda : comenzi) {
            super.add(comanda);
        }
    }

    @Override
    public void add(Comanda c) throws RepositoryException{
        super.add(c);
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO comenzi VALUES(?, ?, ?)")) {
                statement.setInt(1, c.getId());
                statement.setString(2, c.getData_livrare());
                ArrayList<Produs> p = c.getProduse();
                String IDs = new String();
                for(Produs prod : p) {
                    IDs = IDs + prod.getId() + ",";
                }
                IDs = IDs.substring(0, IDs.length() - 1);
                statement.setString(3, IDs);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException{
        super.removeById(id);
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM comenzi WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    @Override
    public void Act(Comanda c) throws RepositoryException{
        super.Act(c);
        try {
            try (PreparedStatement statement = conn.prepareStatement(
                    "UPDATE comenzi SET data_livrare = ?, lista_produse = ? WHERE id = ?")) {
                statement.setString(1, c.getData_livrare());
                ArrayList<Produs> p = c.getProduse();
                String IDs = new String();
                for(Produs prod : p) {
                    IDs = IDs + prod.getId() + ",";
                }
                IDs = IDs.substring(0, IDs.length() - 1);
                statement.setString(2, IDs);
                statement.setInt(3, c.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }

    }

    public ArrayList<Comanda> getAll() {
        ArrayList<Comanda> comenzi = new ArrayList<>();

        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from comenzi"); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    ArrayList<Produs> p = new ArrayList<>();
                    String[] IDs = rs.getString("lista_produse").split(",");

                    for(Produs prod: produse){
                        for(String id: IDs){
                            if(prod.getId() == Integer.parseInt(id)){
                                    p.add(prod);
                            }

                        }
                    }

                    Comanda c = new Comanda(rs.getInt("id"), rs.getString("data_livrare"), p);
                    comenzi.add(c);
                }
            }
        } catch (SQLException | ParseException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return comenzi;
    }

}
