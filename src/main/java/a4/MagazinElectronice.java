package a4;

import a4.Domain.Comanda;
import a4.Domain.Produs;
import a4.Repository.Repository;
import a4.Service.Service;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class MagazinElectronice extends Application {

    private Service service = Main.service;

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label titleLabel = new Label("Gestionare Produse și Comenzi");

        Button addProdButton = new Button("Adaugă Produs");
        Button displayProdButton = new Button("Afișează Produse");
        Button deleteProdButton = new Button("Șterge Produs");
        Button updateProdButton = new Button("Actualizează Produs");

        addProdButton.setOnAction(e -> showAddProdDialog());
        displayProdButton.setOnAction(e -> showProductList(primaryStage));
        deleteProdButton.setOnAction(e -> showDeleteProdDialog());
        updateProdButton.setOnAction(e -> showUpdateProdDialog());

        Button addComButton = new Button("Adaugă Comandă");
        Button displayComButton = new Button("Afișează Comenzi");
        Button deleteComButton = new Button("Șterge Comandă");
        Button updateComButton = new Button("Actualizează Comandă");

        addComButton.setOnAction(e -> showAddComDialog());
        displayComButton.setOnAction(e -> showOrderList(primaryStage));
        deleteComButton.setOnAction(e -> showDeleteComDialog());
        updateComButton.setOnAction(e -> showUpdateComDialog());

        root.getChildren().addAll(titleLabel,
                new Label("Produse:"), addProdButton, displayProdButton, deleteProdButton, updateProdButton,
                new Label("Comenzi:"), addComButton, displayComButton, deleteComButton, updateComButton);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Gestionare Produse și Comenzi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddProdDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Adaugă Produs");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField categorieField = new TextField();
        categorieField.setPromptText("Categorie");

        TextField numeField = new TextField();
        numeField.setPromptText("Nume");

        TextField pretField = new TextField();
        pretField.setPromptText("Preț");

        Button saveButton = new Button("Salvează");
        saveButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String categorie = categorieField.getText();
                String nume = numeField.getText();
                int pret = Integer.parseInt(pretField.getText());

                service.AddProd(id, categorie, nume, pret);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la adăugare produs: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, categorieField, numeField, pretField, saveButton);

        Scene scene = new Scene(layout, 300, 200);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showProductList(Stage primaryStage) {
        Stage listStage = new Stage();
        listStage.setTitle("Lista Produse");

        TableView<Produs> tableView = new TableView<>();

        TableColumn<Produs, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));

        TableColumn<Produs, String> categorieColumn = new TableColumn<>("Categorie");
        categorieColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCategorie()));

        TableColumn<Produs, String> numeColumn = new TableColumn<>("Nume");
        numeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getNume()));

        TableColumn<Produs, Integer> pretColumn = new TableColumn<>("Preț");
        pretColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPret()));

        tableView.getColumns().addAll(idColumn, categorieColumn, numeColumn, pretColumn);

        try {
            tableView.getItems().addAll(service.GetProduse());
        } catch (Exception ex) {
            showErrorDialog("Eroare la încărcarea produselor: " + ex.getMessage());
        }

        VBox layout = new VBox(tableView);
        Scene scene = new Scene(layout, 400, 300);

        listStage.setScene(scene);
        listStage.show();
    }

    private void showDeleteProdDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Șterge Produs");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        Button deleteButton = new Button("Șterge");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                service.StergeProd(id);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la ștergerea produsului: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, deleteButton);

        Scene scene = new Scene(layout, 300, 150);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showUpdateProdDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Actualizează Produs");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField categorieField = new TextField();
        categorieField.setPromptText("Categorie Nouă");

        TextField numeField = new TextField();
        numeField.setPromptText("Nume Nou");

        TextField pretField = new TextField();
        pretField.setPromptText("Preț Nou");

        Button updateButton = new Button("Actualizează");
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String categorie = categorieField.getText();
                String nume = numeField.getText();
                int pret = Integer.parseInt(pretField.getText());

                service.ActProd(id, categorie, nume, pret);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la actualizare produs: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, categorieField, numeField, pretField, updateButton);

        Scene scene = new Scene(layout, 300, 250);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showAddComDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Adaugă Comandă");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField dataField = new TextField();
        dataField.setPromptText("Data");

        TextField nrProdField = new TextField();
        nrProdField.setPromptText("Număr Produse");

        TextField prodIdsField = new TextField();
        prodIdsField.setPromptText("ID-uri Produse (separate prin virgulă)");

        Button saveButton = new Button("Salvează");
        saveButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String data = dataField.getText();
                int nrProd = Integer.parseInt(nrProdField.getText());
                String[] prodIdsStr = prodIdsField.getText().split(",");
                int[] prodIds = new int[prodIdsStr.length];
                for (int i = 0; i < prodIdsStr.length; i++) {
                    prodIds[i] = Integer.parseInt(prodIdsStr[i].trim());
                }

                service.AddCom(id, data, nrProd, prodIds);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la adăugare comandă: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, dataField, nrProdField, prodIdsField, saveButton);

        Scene scene = new Scene(layout, 300, 300);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showOrderList(Stage primaryStage) {
        Stage listStage = new Stage();
        listStage.setTitle("Lista Comenzi");

        TableView<Comanda> tableView = new TableView<>();

        TableColumn<Comanda, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));

        TableColumn<Comanda, String> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getData_livrare()));

        TableColumn<Comanda, String> produseColumn = new TableColumn<>("Produse");
        produseColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getProduse().toString()));

        tableView.getColumns().addAll(idColumn, dataColumn, produseColumn);

        try {
            tableView.getItems().addAll(service.GetComenzi());
        } catch (Exception ex) {
            showErrorDialog("Eroare la încărcarea comenzilor: " + ex.getMessage());
        }

        VBox layout = new VBox(tableView);
        Scene scene = new Scene(layout, 400, 300);

        listStage.setScene(scene);
        listStage.show();
    }

    private void showDeleteComDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Șterge Comandă");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        Button deleteButton = new Button("Șterge");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                service.StergeCom(id);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la ștergerea comenzii: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, deleteButton);

        Scene scene = new Scene(layout, 300, 150);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showUpdateComDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Actualizează Comandă");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField dataField = new TextField();
        dataField.setPromptText("Data Nouă");

        TextField nrProdField = new TextField();
        nrProdField.setPromptText("Număr Produse");

        TextField prodIdsField = new TextField();
        prodIdsField.setPromptText("ID-uri Produse Noi (separate prin virgulă)");

        Button updateButton = new Button("Actualizează");
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String data = dataField.getText();
                int nrProd = Integer.parseInt(nrProdField.getText());
                String[] prodIdsStr = prodIdsField.getText().split(",");
                int[] prodIds = new int[prodIdsStr.length];
                for (int i = 0; i < prodIdsStr.length; i++) {
                    prodIds[i] = Integer.parseInt(prodIdsStr[i].trim());
                }

                service.ActCom(id, data, nrProd, prodIds);
                dialog.close();
            } catch (Exception ex) {
                showErrorDialog("Eroare la actualizare comandă: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(idField, dataField, nrProdField, prodIdsField, updateButton);

        Scene scene = new Scene(layout, 300, 300);
        dialog.setScene(scene);
        dialog.show();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



