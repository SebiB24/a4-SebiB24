package a4.Domain;

public class ProdusFactory implements IEntitateFactory<Produs>{

    @Override
    public String toString(Produs produs) {
        return produs.getId() + "," + produs.getCategorie() + "," + produs.getNume() + "," + produs.getPret();
    }

    @Override
    public Produs fromString(String line) {
        String[] campuri = line.split(",");
        int id = Integer.parseInt(campuri[0]);
        String categorie = campuri[1];
        String nume = campuri[2];
        int pret = Integer.parseInt(campuri[3]);
        return new Produs(id, categorie, nume, pret);
    }
}
