package a4.Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Comanda extends Entitate {
    private Date data_livrare;
    private ArrayList<Produs> produse;


    public Comanda(int id, String date, ArrayList<Produs> produse) throws ParseException {
        super(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.data_livrare = formatter.parse(date);
        if(produse.isEmpty()){
            throw new ParseException("Comanda trebuie sa contina cel putin 1 produs !!", 0);
        }
        this.produse = produse;
    }

    public Comanda(int id, String date) throws ParseException {
        super(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.data_livrare = formatter.parse(date);
        this.produse = new ArrayList<Produs>();
    }

    public void addProdus(Produs produs) {
        this.produse.add(produs);
    }

    public String getData_livrare() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(data_livrare);
    }

    public ArrayList<Produs> getProduse() {
        return produse;
    }

    private String CreazaListaID(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<produse.size(); i++){
            sb.append(produse.get(i).getId());
            if(i != produse.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return super.toString() +";"+ formatter.format(data_livrare) + ";" +CreazaListaID();
    }
}
