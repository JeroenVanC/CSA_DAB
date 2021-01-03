package be.kuleuven.csa.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BeheerKlantenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;

    @FXML
    private TableView<Klant> tblKlanten;

    @FXML
    public TableColumn<Boerderij, Integer> ID;
    @FXML
    public TableColumn<Boerderij, String> naam;
    @FXML
    public TableColumn<Boerderij, String> locatie;
    @FXML
    public TableColumn<Boerderij, Integer> leeftijd;

    @FXML
    private TextField textfieldLocatie;
    @FXML
    private TextField textfieldNaam;
    @FXML
    private TextField textfieldLeeftijd;


    public void initialize() {

        tblKlanten.setOnMouseClicked(e -> selectColumm());
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
        tblKlanten.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ID.setCellValueFactory(new PropertyValueFactory<>("klant_ID"));
        naam.setCellValueFactory(new PropertyValueFactory<>("klant_naam"));
        locatie.setCellValueFactory(new PropertyValueFactory<>("klant_adress"));
        leeftijd.setCellValueFactory(new PropertyValueFactory<>("klant_leeftijd"));

        tblKlanten.setItems(getKlanten());
    }


    private ObservableList<Klant> getKlanten() {
        var jdbiRepo = new RepositoryJdbi();
        return jdbiRepo.getKlanten();
    }

    private void addNewRow() {
        var jdbiRepo = new RepositoryJdbi();

        String naam = textfieldNaam.getText();
        String locatie = textfieldLocatie.getText();
        String leeftijd = textfieldLeeftijd.getText();


        if (textfieldNaam.getText().isBlank() || textfieldLocatie.getText().isBlank() || textfieldLeeftijd.getText().isBlank()) {
            showAlert("MAG NIET!", "textfields are empty");
        } else {
            try {
                int n = Integer.valueOf(leeftijd);
                if (n < 1 || n > 100) {
                    showAlert("MAG NIET", "leeftijd moet tussen 0 en 100 liggen");
                } else {
                    jdbiRepo.insertKlant(naam, locatie, leeftijd);
                }
            } catch (NumberFormatException nfe) {
                showAlert("MAG NIET!", "Leeftijd moet een getal zijn");
            }
            ;

        }
        initTable();
        textfieldLeeftijd.clear();
        textfieldLocatie.clear();
        textfieldNaam.clear();
    }

    private void deleteCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();
        Klant selectedKlant = tblKlanten.getSelectionModel().getSelectedItem();

        if (selectedKlant != null) {
            jdbiRepo.deleteBoerderij(selectedKlant.getKlant_ID(), "KLANT", "klant_ID");
            initTable();
        }
        initTable();
        textfieldLeeftijd.clear();
        textfieldLocatie.clear();
        textfieldNaam.clear();
    }

    private void modifyCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();

        Klant selectedKlant = tblKlanten.getSelectionModel().getSelectedItem();

        String naam = textfieldNaam.getText();
        String locatie = textfieldLocatie.getText();
        String leeftijd = textfieldLeeftijd.getText();

        if (textfieldNaam.getText().isBlank() || textfieldLocatie.getText().isBlank() || textfieldLeeftijd.getText().isBlank() || selectedKlant == null) {
            showAlert("MAG NIET!", "TEXTFIELDs Empty || NO ITEM Selected ");
        } else {

            jdbiRepo.updateKlant(naam, locatie, leeftijd, selectedKlant.getKlant_ID());

        }
        initTable();
        textfieldLeeftijd.clear();
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
        if (tblKlanten.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een boer selecteren hé.");
        }
    }

    private void selectColumm() {
        Klant selectedKlant = tblKlanten.getSelectionModel().getSelectedItem();

        if (selectedKlant != null) {
            textfieldNaam.setText(selectedKlant.getKlant_naam());
            textfieldLocatie.setText(selectedKlant.getKlant_adress());
            textfieldLeeftijd.setText(selectedKlant.getKlant_leeftijd().toString());
        }
    }

}
