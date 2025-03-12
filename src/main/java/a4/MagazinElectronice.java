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
import java.util.List;
import java.util.Set;

public class MagazinElectronice extends Application {

    private Service service = Main.service;

    @Override
    public void start(Stage primaryStage) {
        // Create scenes
        Scene produseScene = createProduseScene(primaryStage);
        Scene comenziScene = createComenziScene(primaryStage);

        primaryStage.setTitle("Magazin App");
        primaryStage.setScene(produseScene);
        primaryStage.show();
    }
    /// PRODUSE SCENE ===============================================================================================================================
    private Scene createProduseScene(Stage primaryStage) {
        HBox root = new HBox();
        ObservableList<Produs> ProduseList = FXCollections.observableArrayList(service.GetProduse());

        TableView<Produs> table = new TableView<>();
        TableColumn<Produs, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Produs, String> numeColumn = new TableColumn<>("Nume");
        TableColumn<Produs, Integer> pretColumn = new TableColumn<>("Pret");
        TableColumn<Produs, String> categorieColumn = new TableColumn<>("Categorie");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        table.getColumns().addAll(idColumn, numeColumn, pretColumn, categorieColumn);
        table.setItems(ProduseList);
        table.setMinWidth(500);

        ///Right Box -------------------------------------------------------------------------------
        VBox RightBox = new VBox();

        Button switchButton = new Button("Comenzi");
        switchButton.setOnAction(e -> primaryStage.setScene(createComenziScene(primaryStage)));
        switchButton.setMaxWidth(Double.MAX_VALUE);
        RightBox.getChildren().add(switchButton);

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

        Button SpecialButton1 = new Button("Afis categorii cu nr produse comandate");
        SpecialButton1.setMaxWidth(Double.MAX_VALUE);
        RightBox.getChildren().add(SpecialButton1);

        Button SpecialButton2 = new Button("Cele mai profitabile luni");
        SpecialButton2.setMaxWidth(Double.MAX_VALUE);
        RightBox.getChildren().add(SpecialButton2);

        Button SpecialButton3 = new Button("Produse sortate dupa incasari");
        SpecialButton3.setMaxWidth(Double.MAX_VALUE);
        RightBox.getChildren().add(SpecialButton3);

        RightBox.setSpacing(15);
        RightBox.setPadding(new Insets(10, 10, 10, 10));

        /// BUTTON FUNCTIONS--------------------------------------------------------------------
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Produs selectedProdus = (Produs) table.getSelectionModel().getSelectedItem();
                idTextField.setText(String.valueOf(selectedProdus.getId()));
                numeTextField.setText(selectedProdus.getNume());
                pretTextField.setText(String.valueOf(selectedProdus.getPret()));
                categorieTextField.setText(selectedProdus.getCategorie());
            }
        });

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
            }
        });

        SpecialButton1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SpecialFunction1();
            }
        });

        SpecialButton2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SpecialFunction2();
            }
        });

        SpecialButton3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ObservableList<Produs> ProduseSortateList = FXCollections.observableArrayList(service.GetProdusSortat());
                    table.setItems(ProduseSortateList);
            }
        });


        root.getChildren().addAll(table, RightBox);
        return new Scene(root, 750, 400);
    }


/// COMENZI SCENE ===============================================================================================================================
    private Scene createComenziScene(Stage primaryStage) {
        HBox root = new HBox();
        ObservableList<Comanda> ComenziList = FXCollections.observableArrayList(service.GetComenzi());

        TableView<Comanda> table = new TableView<>();
        TableColumn<Comanda, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Comanda, String> dataColumn = new TableColumn<>("Data Livrare");
        TableColumn<Comanda, ArrayList<Produs>> produseColumn = new TableColumn<>("Lista Produse");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data_livrare"));
        produseColumn.setCellValueFactory(new PropertyValueFactory<>("produse"));

        table.getColumns().addAll(idColumn, dataColumn, produseColumn);
        table.setItems(ComenziList);
        table.setMinWidth(500);


        ///Right Box -------------------------------------------------------------------------------
        VBox RightBox = new VBox();
        Button switchButton = new Button("Produse");
        switchButton.setOnAction(e -> primaryStage.setScene(createProduseScene(primaryStage)));
        switchButton.setMaxWidth(Double.MAX_VALUE);
        RightBox.getChildren().add(switchButton);

        GridPane cLabelsAndFieldsPane = new GridPane();
        cLabelsAndFieldsPane.setVgap(3.5);
        cLabelsAndFieldsPane.setHgap(3.5);
        Label idLabel = new Label("ID");
        Label dataLabel = new Label("Data livrare");
        Label produseLabel = new Label("Produse");
        TextField idTextField = new TextField();
        TextField dataTextField = new TextField();
        TextField produseTextField = new TextField();

        cLabelsAndFieldsPane.add(idLabel, 0, 0);
        cLabelsAndFieldsPane.add(idTextField, 1, 0);
        cLabelsAndFieldsPane.add(dataLabel, 0, 1);
        cLabelsAndFieldsPane.add(dataTextField, 1, 1);
        cLabelsAndFieldsPane.add(produseLabel, 0, 2);
        cLabelsAndFieldsPane.add(produseTextField, 1, 2);

        RightBox.getChildren().add(cLabelsAndFieldsPane);

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

        Button SpecialButton1 = new Button("Afis categorii cu nr produse comandate");
        SpecialButton1.setMaxWidth(Double.MAX_VALUE);
        RightBox.getChildren().add(SpecialButton1);

        Button SpecialButton2 = new Button("Cele mai profitabile luni");
        SpecialButton2.setMaxWidth(Double.MAX_VALUE);
        RightBox.getChildren().add(SpecialButton2);

        RightBox.setSpacing(15);
        RightBox.setPadding(new Insets(10, 10, 10, 10));

        ///BUTTON FUNCTIONS ------------------------------------------------------------------------------------
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Comanda selctedComanda = (Comanda) table.getSelectionModel().getSelectedItem();
                idTextField.setText(String.valueOf(selctedComanda.getId()));
                dataTextField.setText(selctedComanda.getData_livrare());
                produseTextField.setText(String.valueOf(selctedComanda.getProduse()));
            }
        });

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    int id = Integer.parseInt(idTextField.getText());
                    service.StergeCom(id);
                    ComenziList.setAll(service.GetComenzi());
                }
                catch (Exception e ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("A aparut o eroare: "+e.getMessage());
                    alert.show();
                }
            }
        });

        SpecialButton1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SpecialFunction1();
            }
        });

        SpecialButton2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SpecialFunction2();
            }
        });

        root.getChildren().addAll(table, RightBox);
        return new Scene(root, 750, 400);
    }

    private void SpecialFunction1(){
        Stage nrProduseStage = new Stage();
        nrProduseStage.setTitle("Numărul de Produse pe Categorie");

        VBox layout = new VBox();
        layout.setSpacing(10);

        try {
            List<String> categorii = service.GetCategories();
            for (String categorie : categorii) {
                int nrProduse = service.NrProduseDeCategorie(categorie);
                Label label = new Label(categorie + ": " + nrProduse);
                layout.getChildren().add(label);
            }
        } catch (Exception ex) {
            Label errorLabel = new Label("Eroare la încărcarea datelor: " + ex.getMessage());
            layout.getChildren().add(errorLabel);
        }

        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout, 150, 400);

        nrProduseStage.setScene(scene);
        nrProduseStage.show();
    }

    private void SpecialFunction2(){
        Stage profitLuniStage = new Stage();
        profitLuniStage.setTitle("Profit pe Luni în An");

        VBox layout = new VBox();
        layout.setSpacing(10);

        try {
            Set<Integer> luni = service.GetLuniAn();
            for (int luna : luni) {
                int nrComenzi = service.GetNrComenziLuna(luna);
                double pretTotal = service.GetPretTotalLuna(luna);
                Label label = new Label("Luna: " + luna + ", Număr comenzi: " + nrComenzi + ", Preț total: " + pretTotal);
                layout.getChildren().add(label);
            }
        } catch (Exception ex) {
            Label errorLabel = new Label("Eroare la încărcarea datelor: " + ex.getMessage());
            layout.getChildren().add(errorLabel);
        }

        Scene scene = new Scene(layout, 300, 350);
        layout.setPadding(new Insets(10, 10, 10, 10));

        profitLuniStage.setScene(scene);
        profitLuniStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



