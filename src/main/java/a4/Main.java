package a4;

import a4.Domain.Comanda;
import a4.Domain.ComandaFactory;
import a4.Domain.Produs;
import a4.Domain.ProdusFactory;
import a4.Repository.*;
import a4.Service.Service;
import a4.UI.UI;
import a4.config.Properties;

import java.util.ArrayList;
/*
TODO
- Design visual ui

 */

public class Main {

    public static Repository<Produs> repProdus;
    public static Repository<Comanda> repComanda;
    public static Service service;
    public static UI ui;

    public static void main(String[] args) {

        try {

            Properties prop = new Properties("src/main/java/a4/data/settings.properties");
            switch (prop.getRepositoryType()) {
                case "binary":
                    repProdus = new BinFileRepo<>(prop.getProdusFile());
                    repComanda = new BinFileRepo<>(prop.getComandaFile());
                    break;
                case "text":
                    ProdusFactory produsFactory = new ProdusFactory();
                    repProdus = new TextFileRepo<>(prop.getProdusFile(), produsFactory);
                    ComandaFactory comandaFactory = new ComandaFactory(repProdus);
                    repComanda= new TextFileRepo<>(prop.getComandaFile(), comandaFactory);
                    break;
                case "sql":
                    repProdus = new SqlRepoProdus(prop.getURL());
                    ArrayList<Produs> produse = repProdus.getList();
                    repComanda = new SqlRepoComanda(prop.getURL(), produse);
                    break;
                default:
                    throw new Exception("Invalid repository type!");
            }

            service = new Service(repComanda, repProdus);
            ui = new UI(service);

            if(prop.getTipRulare().equals("interfata vizuala")){
                MagazinElectronice.launch(MagazinElectronice.class, args);
            }else{
                if(prop.getTipRulare().equals("linie de comanda")){
                    ui.program();
                }
                else
                    throw new Exception("Invalid run type!");
            }


        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
