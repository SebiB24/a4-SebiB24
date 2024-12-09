package org.example.a4sebib24;

import org.example.a4sebib24.Domain.Comanda;
import org.example.a4sebib24.Domain.ComandaFactory;
import org.example.a4sebib24.Domain.Produs;
import org.example.a4sebib24.Domain.ProdusFactory;
import org.example.a4sebib24.Repository.AbstractFileRepo;
import org.example.a4sebib24.Repository.BinFileRepo;
import org.example.a4sebib24.Repository.TextFileRepo;
import org.example.a4sebib24.Service.Service;
import org.example.a4sebib24.UI.UI;
import org.example.a4sebib24.config.Properties;

public class Main {
    public static void main(String[] args) {


        try {

            AbstractFileRepo<Produs> repProdus;
            AbstractFileRepo<Comanda> repComanda;

            Properties prop = new Properties("src/main/java/org/example/a4sebib24/data/settings.properties");
            switch (prop.getRepositoryType()) {
                case "binary":
                    repProdus = new BinFileRepo<Produs>(prop.getProdusFile());
                    repComanda = new BinFileRepo<Comanda>(prop.getComandaFile());
                    break;
                case "text":
                    ProdusFactory produsFactory = new ProdusFactory();
                    repProdus = new TextFileRepo<Produs>(prop.getProdusFile(), produsFactory);
                    ComandaFactory comandaFactory = new ComandaFactory(repProdus);
                    repComanda= new TextFileRepo<Comanda>(prop.getComandaFile(), comandaFactory);
                    break;
                default:
                    throw new Exception("Invalid repository type!");
            }

            Service serv = new Service(repComanda, repProdus);
            UI ui = new UI(serv);
            ui.program();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
