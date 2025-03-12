package a4.Repository;

import a4.Domain.Produs;
import com.github.javafaker.Faker;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SqlRepoProdus extends Repository<Produs> {
    protected String URL;
    protected Connection conn = null;

    public SqlRepoProdus(String URL) throws RepositoryException {
        this.URL = URL;
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
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS produse(id int, categorie varchar(200), nume varchar(300), pret int);");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    protected void initTables() {

        Faker faker = new Faker();

        List<String> tipProdus = Arrays.asList("Telefon", "Tableta", "Laptop", "Smartwatch", "Desktop"
                , "Baterie externa", "Tastatura", "Monitor", "Mouse", "Televizor", "Frigider", "Masina de spalat");
        Random rand = new Random();

        try {
            try (PreparedStatement statement1 = conn.prepareStatement("SELECT COUNT(*) from produse")){
                statement1.executeQuery().next();
                if (statement1.executeQuery().getInt(1) == 0)
                    for (int i = 0; i < 50; i++) {
                        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO produse VALUES(?, ?, ?, ?)")) {
                            statement.setInt(1, i);
                            statement.setString(2, tipProdus.get(rand.nextInt(tipProdus.size())));
                            statement.setString(3, faker.company().name().replace(",", "") + " " + faker.bothify("??###"));
                            statement.setInt(4, faker.number().numberBetween(100, 5000));
                            statement.executeUpdate();
                        }
                    }
            }
        }catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }
    }

    protected void initRepo() throws RepositoryException{
        ArrayList<Produs> produses = getAll();
        for(Produs produs : produses) {
            super.add(produs);
        }
    }

    public ArrayList<Produs> getAll() {
        ArrayList<Produs> produse = new ArrayList<>();

        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from produse"); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Produs p = new Produs(rs.getInt("id"), rs.getString("categorie")
                            , rs.getString("nume"), rs.getInt("pret"));
                    produse.add(p);
                }
            }
        } catch (SQLException ex) {
            System.err.println("[ERROR] getAll : " + ex.getMessage());
        }

        return produse;
    }

    @Override
    public void add(Produs p) throws RepositoryException{
        super.add(p);
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO produse VALUES(?, ?, ?, ?)")) {
                statement.setInt(1, p.getId());
                statement.setString(2, p.getCategorie());
                statement.setString(3, p.getNume());
                statement.setInt(4, p.getPret());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException{
        super.removeById(id);
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM produse WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] delete : " + e.getMessage());
        }
    }

    @Override
    public void Act(Produs p) throws RepositoryException{
        super.Act(p);
        try {
            try (PreparedStatement statement = conn.prepareStatement(
                    "UPDATE produse SET categorie = ?, nume = ?, pret = ? WHERE id = ?")) {
                statement.setString(1, p.getCategorie());
                statement.setString(2, p.getNume());
                statement.setInt(3, p.getPret());
                statement.setInt(4, p.getId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] insert : " + e.getMessage());
        }

    }

}
