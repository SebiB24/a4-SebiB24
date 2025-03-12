package a4;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MagazinElectronice extends Application {

    private Service service = Main.service;

    // Obiect = "Produse"/"Comenzi" // determina obiectul actual de lucru
    private String Obiect = "Produse";

    @Override
    public void start(Stage primaryStage) {

        HBox mainBox = new HBox();

        HBox leftSideBox = new HBox();

        ObservableList<Produs> ProduseList = FXCollections.observableArrayList(service.GetProduse());

        /// Crearea LISTA pentru afisarea datelor

        ListView<Produs> ProduseListView = new ListView<>();
        ProduseListView.setItems(ProduseList);

        ProduseListView.setMinWidth(275);
        ProduseListView.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(ProduseListView, Priority.ALWAYS);


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


        //creem o lista de musicians care se vor afisa in tabel
        ProdusTable.setItems(ProduseList);
        ProdusTable.setMaxWidth(300);

        /// Adaugare Tabel/Lista in LBox

        leftSideBox.getChildren().add(ProdusTable);
        leftSideBox.setPadding(new Insets(10, 10, 10, 10));

        /// Adaugare LBox in MBox

        mainBox.getChildren().add(leftSideBox);


        VBox CenterBox = new VBox();

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

        CenterBox.getChildren().add(labelsAndFieldsPane);

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


        CenterBox.getChildren().add(buttonBox);

        CenterBox.setSpacing(15);
        CenterBox.setPadding(new Insets(10, 10, 10, 10));

        mainBox.getChildren().add(CenterBox);

        VBox RightSideBox = new VBox();
        RightSideBox.setPadding(new Insets(10, 10, 10, 10));
        RightSideBox.setSpacing(5);

        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        Button resetButton = new Button("Reset");
        searchButton.setMaxWidth(Double.MAX_VALUE);
        resetButton.setMaxWidth(Double.MAX_VALUE);

        RightSideBox.getChildren().add(searchField);
        RightSideBox.getChildren().add(searchButton);
        RightSideBox.getChildren().add(resetButton);

        TextField createField = new TextField();
        Button createButton = new Button("Create List");
        createButton.setMaxWidth(Double.MAX_VALUE);

        RightSideBox.getChildren().add(createField);
        RightSideBox.getChildren().add(createButton);

        mainBox.getChildren().add(RightSideBox);


        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    int id = Integer.parseInt(idTextField.getText());
                    String nume = numeTextField.getText();
                    int pret = Integer.parseInt(pretTextField.getText());
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

                System.out.println("We pressed the add button.");
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
                System.out.println("We pressed the delete button.");
            }
        });

        updateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    int id = Integer.parseInt(idTextField.getText());
                    String nume = numeTextField.getText();
                    int pret = Integer.parseInt(pretTextField.getText());
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
                System.out.println("We pressed the update button.");
            }
        });

        ProdusTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ProdusTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Produs selectedProdus = (Produs) ProdusTable.getSelectionModel().getSelectedItem();
                idTextField.setText(String.valueOf(selectedProdus.getId()));
                numeTextField.setText(selectedProdus.getNume());
                pretTextField.setText(String.valueOf(selectedProdus.getPret()));
                categorieTextField.setText(selectedProdus.getCategorie());
            }
        });

        Scene scene = new Scene(mainBox, 750, 400);
        primaryStage.setTitle("Magazin App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}



