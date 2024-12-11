package a4.Domain;

public class Produs extends Entitate {
    private String categorie;
    private String nume;
    private int pret;

    public Produs(int id, String categorie, String nume, int pret){
        super(id);
        this.nume = nume;
        this.pret = pret;
        this.categorie = categorie;
    }

    public String getCategorie(){
        return categorie;
    }

    public String getNume(){
        return nume;
    }

    public int getPret(){
        return pret;
    }

    public void setCategorie(String categorie){
        this.categorie = categorie;
    }

    public void setNume(String nume){
        this.nume = nume;
    }

    public void setPret(int pret){
        this.pret = pret;
    }

    @Override
    public String toString(){
        return super.toString() + "," + this.categorie + "," + this.nume + "," + this.pret;
    }
}
