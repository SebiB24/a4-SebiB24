package a4.Service;
import a4.Domain.Comanda;
import a4.Domain.Produs;
import a4.Repository.Repository;

import java.util.ArrayList;

public class Service {
    private Repository<Comanda> repoC;
    private Repository<Produs> repoP;

    public Service(Repository<Comanda> rc, Repository<Produs> rp) {
        repoC = rc;
        repoP = rp;
    }

    public void AddProd(int id, String categorie, String nume, int pret)throws Exception{
        Produs prod = new Produs(id, categorie, nume, pret);
            repoP.add(prod);
    }

    public void AddCom(int id, String data, int nrProd, int[] p)throws Exception{
        ArrayList<Produs> produse = new ArrayList<>();
        for(int i=0; i<nrProd; i++) {
            produse.add(repoP.getElementById(p[i]));
        }

        Comanda com = new Comanda(id, data, produse);
        repoC.add(com);
    }

    public ArrayList<Produs> GetProduse(){
        return repoP.getList();
    }

    public ArrayList<Comanda> GetComenzi(){
        return repoC.getList();
    }

    public void StergeProd(int id) throws Exception{
        repoP.removeById(id);
    }

    public void StergeCom(int id) throws Exception{
        repoC.removeById(id);
    }

    public void ActProd(int id, String categorie, String nume, int pret)throws Exception{
        Produs prod = new Produs(id, categorie, nume, pret);
        repoP.Act(prod);
    }

    public void ActCom(int id, String data, int nrProd, int[] p)throws Exception{
        ArrayList<Produs> produse = new ArrayList<>();
        for(int i=0; i<nrProd; i++){
            produse.add(repoP.getElementById(p[i]));
        }
        Comanda comanda = new Comanda(id, data, produse);
        repoC.Act(comanda);
    }
}
