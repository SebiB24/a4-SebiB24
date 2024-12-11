package a4.Domain;
import a4.Repository.AbstractFileRepo;
import a4.Repository.Repository;
import a4.Repository.RepositoryException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ComandaFactory implements IEntitateFactory<Comanda>{

    Repository<Produs> repProdus;

    public ComandaFactory(Repository<Produs> repProdus) {
        this.repProdus = repProdus;
    }

    @Override
    public String toString(Comanda comanda) {
        ArrayList<Produs> produse = comanda.getProduse();
        String IDProduse = "";
        for(Produs element: produse){
            IDProduse = IDProduse + Integer.toString(element.getId()) + ",";
        }
        IDProduse = IDProduse.substring(0, IDProduse.length()-1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return comanda.getId() + ";" + formatter.format(comanda.getData_livrare()) + ";" + IDProduse;
    }

    @Override
    public Comanda fromString(String line) throws ParseException, RepositoryException {
        String[] campuri = line.split(";");
        int id = Integer.parseInt(campuri[0]);
        String data = campuri[1];

        ArrayList<Produs> produse = new ArrayList<>();
        String[] nrID = campuri[2].split(",");

        for(int i = 0; i < nrID.length; i++){
            produse.add(this.repProdus.getElementById(Integer.parseInt(nrID[i])));
        }
        return new Comanda(id, data, produse);
    }
}
