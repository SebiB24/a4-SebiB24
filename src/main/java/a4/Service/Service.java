package a4.Service;
import a4.Domain.Comanda;
import a4.Domain.Produs;
import a4.Repository.Repository;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    private int[] StringToArray(String str) {
        String[] parts = str.replaceAll("[\\[\\] ]", "").split(",");

        return Arrays.stream(parts)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public void AddCom(int id, String data, String p)throws Exception{
        ArrayList<Produs> produse = new ArrayList<>();

        int[] Ids = StringToArray(p);
        for (int i : Ids) {
            produse.add(repoP.getElementById(i));
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

    public List<String> GetCategories(){
        List<String> categori = repoP.getList().stream()
                .map(Produs::getCategorie)
                .distinct()
                .collect(Collectors.toList());
        return categori;
    }

    public int NrProduseDeCategorie(String categorie){
        ArrayList<Comanda> comenzi = repoC.getList();
        return comenzi.stream()
                .flatMap(comanda -> comanda.getProduse().stream())
                .filter(produs -> produs.getCategorie().equals(categorie))
                .mapToInt(produs -> 1)
                .sum();
    }

    public Set<Integer> GetLuniAn() throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Comanda> comenzi = repoC.getList();

        return comenzi.stream()
                .map(Comanda::getData_livrare)
                .map(data -> LocalDate.parse(data, formatter))
                .map(LocalDate::getMonthValue)
                .collect(Collectors.toSet());
    }

    public int GetNrComenziLuna(int luna){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Comanda> comenzi = repoC.getList();

        return (int) comenzi.stream()
                .map(Comanda::getData_livrare)
                .map(data -> LocalDate.parse(data, formatter))
                .filter(date -> date.getMonthValue() == luna)
                .count();
    }

    public int GetPretTotalLuna(int luna){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ArrayList<Comanda> comenzi = repoC.getList();

        return comenzi.stream()
                .filter(comanda -> {
                    LocalDate date = LocalDate.parse(comanda.getData_livrare(), formatter);
                    return date.getMonthValue() == luna;
                })
                .flatMap(comanda -> comanda.getProduse().stream())
                .mapToInt(Produs::getPret)
                .sum();
    }

    public int IncasariProdus(Produs produs){
        List<Comanda> comenzi = repoC.getList();

        // Use streams to count occurrences of the product and calculate total revenue
        return comenzi.stream()
                .flatMap(comanda -> comanda.getProduse().stream())
                .filter(p -> p.getId() == produs.getId())
                .mapToInt(p -> produs.getPret())
                .sum();
    }

    public ArrayList<Produs> GetProdusSortat(){
        ArrayList<Produs> produse = repoP.getList();
        produse.sort((p1, p2) -> Integer.compare(IncasariProdus(p2), IncasariProdus(p1)));
        return produse;
    }
}
