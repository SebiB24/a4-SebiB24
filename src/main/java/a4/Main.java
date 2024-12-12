package a4;

import a4.Domain.Comanda;
import a4.Domain.ComandaFactory;
import a4.Domain.Produs;
import a4.Domain.ProdusFactory;
import a4.Repository.*;
import a4.Service.Service;
import a4.UI.UI;
import a4.config.Properties;
/*
TODO
- SQL la comenzi lista de produse
- Design visual ui
- adauga functionalitate 9, 10 in VI

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
                    repComanda = new SqlRepoComanda(prop.getURL());
                    break;
                default:
                    throw new Exception("Invalid repository type!");
            }

            service = new Service(repComanda, repProdus);
            ui = new UI(service);

            MagazinElectronice.launch(MagazinElectronice.class, args);
            //ui.program();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
