package be.kuleuven.csa.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BeheerBoerderijenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;

    @FXML
    private TableView<Boerderij> tblBoerderijen;

    @FXML
    public TableColumn<Boerderij, Integer> boerderij_ID;

    @FXML
    public TableColumn<Boerderij, String> naam;

    @FXML
    public TableColumn<Boerderij, String> locatie;

    @FXML
    private TextField textfieldLocatie;
    @FXML
    private TextField textfieldNaam;


    public void initialize() {

        tblBoerderijen.setOnMouseClicked(e -> selectColumm());
        initTable();
        btnAdd.setOnAction(e -> addNewRow());
        btnModify.setOnAction(e -> {
            modifyCurrentRow();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void initTable() {
        tblBoerderijen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // tblBoerderijen.getColumns().clear();

        // TODO verwijderen en "echte data" toevoegen!

        boerderij_ID.setCellValueFactory(new PropertyValueFactory<>("boerderij_ID"));
        naam.setCellValueFactory(new PropertyValueFactory<>("boerderij_naam"));
        locatie.setCellValueFactory(new PropertyValueFactory<>("boerderij_locatie"));

        tblBoerderijen.setItems(getBoerderijen());
    }

    private ObservableList<Boerderij> getBoerderijen() {
        var jdbiRepo = new RepositoryJdbi();
        return jdbiRepo.getBoerderijen();
    }

    private void addNewRow() {
        var jdbiRepo = new RepositoryJdbi();

        String naam = textfieldNaam.getText();
        String locatie = textfieldLocatie.getText();

        if (textfieldNaam.getText().isBlank() || textfieldLocatie.getText().isBlank()) {
            showAlert("MAG NIET!", "Vul iets in eh!!");
        } else {
            jdbiRepo.insertBoerderij(naam, locatie);
        }
        initTable();
        textfieldLocatie.clear();
        textfieldNaam.clear();
    }

    private void deleteCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();
        Boerderij selectedBoerderij = tblBoerderijen.getSelectionModel().getSelectedItem();

        if (selectedBoerderij != null) {
            jdbiRepo.deleteBoerderij(selectedBoerderij.getBoerderij_ID(), "BOERDERIJ", "boerderij_ID");
            initTable();
        }
        initTable();
        textfieldLocatie.clear();
        textfieldNaam.clear();
    }

    private void modifyCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();

        Boerderij selectedBoerderij = tblBoerderijen.getSelectionModel().getSelectedItem();

        String naam = textfieldNaam.getText();
        String locatie = textfieldLocatie.getText();

        if (textfieldNaam.getText().isBlank() || textfieldLocatie.getText().isBlank() || selectedBoerderij == null) {
            showAlert("MAG NIET!", "TEXTFIELD Empty || NO ITEM Selected ");
        } else {

            jdbiRepo.updateBoerderij(naam, locatie, selectedBoerderij.getBoerderij_ID());

        }
        initTable();
        textfieldLocatie.clear();
        textfieldNaam.clear();

    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if (tblBoerderijen.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een boer selecteren hé.");
        }
    }

    private void selectColumm() {
        Boerderij selectedBoerderij = tblBoerderijen.getSelectionModel().getSelectedItem();

        if (selectedBoerderij != null) {
            textfieldNaam.setText(selectedBoerderij.getBoerderij_naam());
            textfieldLocatie.setText(selectedBoerderij.getBoerderij_locatie());
        }
    }
}
