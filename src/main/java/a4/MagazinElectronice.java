package a4;

import a4.Domain.Comanda;
import a4.Domain.Produs;
import a4.Service.Service;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MagazinElectronice extends Application {

    private Service service = Main.service;

    // Obiect = "Produse"/"Comenzi" // determina obiectul actual de lucru
    private String Obiect = "Produse";

    private String getOtherObject(String obj){
        if(obj.equals("Produse")){
            return "Comenzi";
        }
        return "Produse";
    }

    @Override
    public void start(Stage primaryStage) {

/// COMENZI BOX ===================================================================================================================
        HBox ProduseBox = new HBox();

        HBox leftSideBox = new HBox();

        ObservableList<Produs> ProduseList = FXCollections.observableArrayList(service.GetProduse());

        /// Creare TABEL pentru afisarea datelor
        /// !!! ADAUGA (opens Domain to javafx.base;) IN (module-info.java) !!!!!!

        TableView<Produs> ProdusTable = new TableView<>();
        //creem cate o coloana pe rand
        //textul din paranteze este header-ul
        TableColumn<Produs, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Produs, String> numeColumn = new TableColumn<>("Nume");
        TableColumn<Produs, Integer> pretColumn = new TableColumn<>("Pret");
        TableColumn<Produs, String> categorieColumn = new TableColumn<>("Categorie");

        //specificam cum se vor completa coloanele - ce camp dintr-un
        //obiect Musician vine pe fiecare coloana

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        //adaugam coloanele la tabel
        ProdusTable.getColumns().add(idColumn);
        ProdusTable.getColumns().add(numeColumn);
        ProdusTable.getColumns().add(pretColumn);
        ProdusTable.getColumns().add(categorieColumn);

        ProdusTable.setItems(ProduseList);
        ProdusTable.setMinWidth(500);

        /// Adaugare Tabel/Lista in LBox

        leftSideBox.getChildren().add(ProdusTable);
        leftSideBox.setPadding(new Insets(10, 10, 10, 10));

        /// Adaugare LBox in MBox

        ProduseBox.getChildren().add(leftSideBox);

        VBox RightBox = new VBox();

        Button objectButton = new Button(getOtherObject(Obiect));
        objectButton.setMaxWidth(Double.MAX_VALUE);
        RightBox.getChildren().add(objectButton);

        GridPane labelsAndFieldsPane = new GridPane();
        labelsAndFieldsPane.setVgap(3.5);
        labelsAndFieldsPane.setHgap(3.5);
        Label idLabel = new Label("ID");
        Label numeLabel = new Label("Nume");
        Label pretLabel = new Label("Pret");
        Label categorieLable = new Label("Categorie");
        TextField idTextField = new TextField();
        TextField numeTextField = new TextField();
        TextField pretTextField = new TextField();
        TextField categorieTextField = new TextField();

        labelsAndFieldsPane.add(idLabel, 0, 0);
        labelsAndFieldsPane.add(idTextField, 1, 0);
        labelsAndFieldsPane.add(numeLabel, 0, 1);
        labelsAndFieldsPane.add(numeTextField, 1, 1);
        labelsAndFieldsPane.add(pretLabel, 0, 2);
        labelsAndFieldsPane.add(pretTextField, 1, 2);
        labelsAndFieldsPane.add(categorieLable, 0, 3);
        labelsAndFieldsPane.add(categorieTextField, 1, 3);

        RightBox.getChildren().add(labelsAndFieldsPane);

        //new HBox pentru butoane
        HBox buttonBox = new HBox();
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button updateButton = new Button("Update");

        buttonBox.getChildren().add(addButton);
        buttonBox.getChildren().add(deleteButton);
        buttonBox.getChildren().add(updateButton);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        buttonBox.setSpacing(10);


        RightBox.getChildren().add(buttonBox);

        RightBox.setSpacing(15);
        RightBox.setPadding(new Insets(10, 10, 10, 10));

        ProduseBox.getChildren().add(RightBox);

/// COMENZI BOX ===================================================================================================================
        HBox ComenziBox = new HBox();

        HBox clLeftSideBox = new HBox();

        ObservableList<Comanda> ComenziList = FXCollections.observableArrayList(service.GetComenzi());

        /// Creare TABEL pentru afisarea datelor
        /// !!! ADAUGA (opens Domain to javafx.base;) IN (module-info.java) !!!!!!

        TableView<Comanda> ComenziTable = new TableView<>();
        //creem cate o coloana pe rand
        //textul din paranteze este header-ul
        TableColumn<Comanda, Integer> cIdColumn = new TableColumn<>("ID");
        TableColumn<Comanda, String> dataColumn = new TableColumn<>("Data Livrare");
        TableColumn<Comanda, ArrayList<Produs>> produseColumn = new TableColumn<>("Lista Produse");

        //specificam cum se vor completa coloanele - ce camp dintr-un
        //obiect Musician vine pe fiecare coloana

        cIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data_livrare"));
        produseColumn.setCellValueFactory(new PropertyValueFactory<>("produse"));

        //adaugam coloanele la tabel
        ComenziTable.getColumns().add(cIdColumn);
        ComenziTable.getColumns().add(dataColumn);
        ComenziTable.getColumns().add(produseColumn);

        ComenziTable.setItems(ComenziList);
        ComenziTable.setMinWidth(500);

        /// Adaugare Tabel/Lista in LBox
        clLeftSideBox.getChildren().add(ComenziTable);
        clLeftSideBox.setPadding(new Insets(10, 10, 10, 10));

        /// Adaugare LBox in MBox
        ProduseBox.getChildren().add(clLeftSideBox);

        VBox cRightBox = new VBox();
        cRightBox.getChildren().add(objectButton);

        GridPane cLabelsAndFieldsPane = new GridPane();
        cLabelsAndFieldsPane.setVgap(3.5);
        cLabelsAndFieldsPane.setHgap(3.5);
        Label cIdLabel = new Label("ID");
        Label dataLabel = new Label("Data livrare");
        Label produseLabel = new Label("Produse");
        TextField cIdTextField = new TextField();
        TextField dataTextField = new TextField();
        TextField produseTextField = new TextField();

        cLabelsAndFieldsPane.add(cIdLabel, 0, 0);
        cLabelsAndFieldsPane.add(cIdTextField, 1, 0);
        cLabelsAndFieldsPane.add(dataLabel, 0, 1);
        cLabelsAndFieldsPane.add(dataTextField, 1, 1);
        cLabelsAndFieldsPane.add(produseLabel, 0, 2);
        cLabelsAndFieldsPane.add(produseTextField, 1, 2);

        cRightBox.getChildren().add(cLabelsAndFieldsPane);

        cRightBox.getChildren().add(buttonBox);

        cRightBox.setSpacing(15);
        cRightBox.setPadding(new Insets(10, 10, 10, 10));

        ProduseBox.getChildren().add(cRightBox);

        Scene produseScene = new Scene(ProduseBox, 750, 400);
        Scene comenziScene = new Scene(ComenziBox, 750, 400);
        primaryStage.setTitle("Magazin App");
        primaryStage.setScene(produseScene);
        primaryStage.show();

/// BUTTON FUNCTIONS ===================================================================================================================
        objectButton.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Obiect = getOtherObject(Obiect);
                objectButton.setText(getOtherObject(Obiect));
            }
        });

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    int id = Integer.parseInt(idTextField.getText());
                    String nume = dataTextField.getText();
                    int pret = Integer.parseInt(produseTextField.getText());
                    String categorie = categorieTextField.getText();
                    service.AddProd(id, categorie, nume, pret);
                    ProduseList.setAll(service.GetProduse());
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }

            }
        });

        objectButton.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Obiect = getOtherObject(Obiect);
                objectButton.setText(getOtherObject(Obiect));

                if(Obiect.equals("Produse")){
                    primaryStage.setScene(produseScene);
                }
                else if(Obiect.equals("Comenzi")){
                    primaryStage.setScene(comenziScene);
                }
            }
        });

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    int id = Integer.parseInt(idTextField.getText());
                    String nume = dataTextField.getText();
                    int pret = Integer.parseInt(produseTextField.getText());
                    String categorie = categorieTextField.getText();
                    service.AddProd(id, categorie, nume, pret);
                    ProduseList.setAll(service.GetProduse());
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }

            }
        });

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    int id = Integer.parseInt(idTextField.getText());
                    service.StergeProd(id);
                    ProduseList.setAll(service.GetProduse());
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }
            }
        });

        updateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    int id = Integer.parseInt(idTextField.getText());
                    String nume = dataTextField.getText();
                    int pret = Integer.parseInt(produseTextField.getText());
                    String categorie = categorieTextField.getText();
                    service.ActProd(id, categorie, nume, pret);
                    ProduseList.setAll(service.GetProduse());
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }
            }
        });

        ProdusTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ProdusTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Produs selectedProdus = (Produs) ProdusTable.getSelectionModel().getSelectedItem();
                idTextField.setText(String.valueOf(selectedProdus.getId()));
                dataTextField.setText(selectedProdus.getNume());
                produseTextField.setText(String.valueOf(selectedProdus.getPret()));
                categorieTextField.setText(selectedProdus.getCategorie());
            }
        });

        ComenziTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ComenziTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Comanda selctedComanda = (Comanda) ComenziTable.getSelectionModel().getSelectedItem();
                cIdTextField.setText(String.valueOf(selctedComanda.getId()));
                dataTextField.setText(selctedComanda.getData_livrare());
                produseTextField.setText(String.valueOf(selctedComanda.getProduse()));
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}



