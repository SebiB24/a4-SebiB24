package a4.UI;
import a4.Domain.Comanda;
import a4.Domain.Produs;
import a4.Service.Service;

import java.util.ArrayList;
import java.util.Scanner;
public class UI {
    private Service Service;

    public UI(Service Service) {
        this.Service = Service;
    }

    private void AddProd(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti ID: ");
        int id = scanner.nextInt();
        System.out.println("Introduceti Categoria: ");
        String categorie = scanner.next();
        System.out.println("Introduceti Nume: ");
        String nume = scanner.next();
        System.out.println("Introduceti Pret: ");
        int pret = scanner.nextInt();
        try{
            Service.AddProd(id, categorie, nume, pret);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void AddCom() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti ID: ");
        int id = scanner.nextInt();
        System.out.println("Introduceti data livrare (yyyy-MM-dd): ");
        String data = scanner.next();
        System.out.println("Introduceti numarul de produse din comanda: ");
        int nrProd = scanner.nextInt();
        int[] p = new int[nrProd];
        for(int i = 0; i < nrProd; i++){
            System.out.println("Introduceti ID produs "+(i+1)+": ");
            p[i] = scanner.nextInt();
        }
        Service.AddCom(id, data, nrProd, p);

    }

    private void AfisProd(){
        ArrayList<Produs> list = Service.GetProduse();
        for(Produs produs: list){
            System.out.println(produs.toString());
        }
    }

    private void AfisCom(){
        ArrayList<Comanda> list = Service.GetComenzi();
        for(Comanda comanda: list){
            System.out.println(comanda.toString());
        }
    }

    private void StergeProd() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti ID: ");
        int id = scanner.nextInt();
        Service.StergeProd(id);
    }

    private void StergeCom() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti ID: ");
        int id = scanner.nextInt();
        Service.StergeCom(id);
    }

    private void ActProd()throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti ID produs existent: ");
        int id = scanner.nextInt();
        System.out.println("INTRODUCETI DATELE NOI ");
        System.out.println("Introduceti categoria: ");
        String categorie = scanner.next();
        System.out.println("Introduceti nume: ");
        String nume = scanner.next();
        System.out.println("Introduceti Pret: ");
        int pret = scanner.nextInt();
        Service.ActProd(id, categorie, nume, pret);
    }

    private void ActCom()throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti ID comanda existenta: ");
        int id = scanner.nextInt();
        System.out.println("INTRODUCETI DATELE NOI ");
        System.out.println("Introduceti data livrare (yyyy-MM-dd): ");
        String data = scanner.next();
        System.out.println("Introduceti numarul de produse din comanda: ");
        int nrProd = scanner.nextInt();
        int[] p = new int[nrProd];
        for(int i = 0; i < nrProd; i++){
            System.out.println("Introduceti ID produs "+(i+1)+": ");
            p[i] = scanner.nextInt();
        }

        Service.ActCom(id, data, nrProd, p);
    }

    public void program(){
        System.out.println("MENIU");
        System.out.println("1.Adauga produs");
        System.out.println("2.Afiseaza produse");
        System.out.println("3.Sterge produs dupa ID");
        System.out.println("4.Actualizare produs");
        System.out.println("5.Adauga comenzi");
        System.out.println("6.Afiseaza comenzi");
        System.out.println("7.Sterge comanda dupa ID");
        System.out.println("8.Actualizare comanda");

        boolean run = true;
        while(run) {
            try{
                Scanner scanner = new Scanner(System.in);
                System.out.print("INTRODUCETI NUMARUL COMENZII: ");

                int n = scanner.nextInt();

                switch (n) {
                    case 1:
                        AddProd();
                        break;
                    case 2:
                        AfisProd();
                        break;
                    case 3:
                        StergeProd();
                        break;
                    case 4:
                        ActProd();
                        break;
                    case 5:
                        AddCom();
                        break;
                    case 6:
                        AfisCom();
                        break;
                    case 7:
                        StergeCom();
                        break;
                    case 8:
                        ActCom();
                        break;
                    case 0:
                        run = false;
                        break;
                    default:
                        System.out.println("!! Comanda invalida !!");
                }
            }
            catch(Exception e){
            System.out.println(e.getMessage());
            }
        }
    }

}
