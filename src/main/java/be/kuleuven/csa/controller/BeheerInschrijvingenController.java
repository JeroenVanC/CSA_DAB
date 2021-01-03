package be.kuleuven.csa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;


public class BeheerInschrijvingenController {

    @FXML
    private Button btnClose;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;

    @FXML
    private TableView<InschrijvingSub> tblInschrijving;
    @FXML
    private TableColumn<Inschrijving, Integer> contract_nr;
    @FXML
    private TableColumn<Inschrijving, String> boerderij;
    @FXML
    private TableColumn<Inschrijving, Integer> boerderijID;
    @FXML
    private TableColumn<Inschrijving, String> pakket;
    @FXML
    private TableColumn<Inschrijving, String> klant;
    @FXML
    private TableColumn<Inschrijving, Integer> klantID;
    @FXML
    private TableColumn<Inschrijving, String> jaar;


    @FXML
    private ChoiceBox<String> choicebox_boerderij;
    @FXML
    private ChoiceBox<String> choicebox_klant;
    @FXML
    private ChoiceBox<String> choicebox_jaar;
    @FXML
    private ChoiceBox<String> choicebox_size;


    public void initialize() {
        tblInschrijving.setOnMouseClicked(e -> selectColumm());
        initTable();
        initCB();
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        btnAdd.setOnAction(e -> addNewRow());
        btnModify.setOnAction(e -> {
            modifyCurrentRow();
        });

        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
        });


    }

    private void modifyCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();

        Inschrijving selectedInschrijving = tblInschrijving.getSelectionModel().getSelectedItem();

        String boerderijValue = choicebox_boerderij.getValue();
        String klantValue = choicebox_klant.getValue();
        String jaarValue = choicebox_jaar.getValue();
        String sizeValue = choicebox_size.getValue();

        if (selectedInschrijving == null) {
            showAlert("MAG NIET!", "TEXTFIELD Empty || NO ITEM Selected ");
        } else {

            jdbiRepo.updateInschrijving(boerderijValue, sizeValue, klantValue, jaarValue, selectedInschrijving.getContract_nr());

        }

        initTable();
        choicebox_boerderij.setValue(null);
        choicebox_klant.setValue(null);
        choicebox_jaar.setValue(null);
        choicebox_size.setValue(null);
    }

    private void verifyOneRowSelected() {

    }

    private void deleteCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();
        Inschrijving selectedInschrijving = tblInschrijving.getSelectionModel().getSelectedItem();

        if (selectedInschrijving != null) {
            jdbiRepo.deleteBoerderij(selectedInschrijving.getContract_nr(), "ZIT_IN_CONTRACT", "contract_nr");
            initTable();
        }
        initTable();
        choicebox_boerderij.setValue(null);
        choicebox_klant.setValue(null);
        choicebox_jaar.setValue(null);
        choicebox_size.setValue(null);
    }

    private void addNewRow() {
        var jdbiRepo = new RepositoryJdbi();


        String boerderijValue = choicebox_boerderij.getValue();
        String klantValue = choicebox_klant.getValue();
        String jaarValue = choicebox_jaar.getValue();
        String sizeValue = choicebox_size.getValue();


        if (choicebox_boerderij.getValue().isBlank() || choicebox_klant.getValue().isBlank() || choicebox_jaar.getValue().isBlank() || choicebox_size.getValue().isBlank()) {
            showAlert("MAG NIET!", "Vul iets in eh!!");
        } else {
            jdbiRepo.insertInschrijving(boerderijValue, sizeValue, klantValue, jaarValue);
        }

        initTable();
        choicebox_boerderij.setValue(null);
        choicebox_klant.setValue(null);
        choicebox_jaar.setValue(null);
        choicebox_size.setValue(null);
    }

    private void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void initCB() {
        var jdbiRepo = new RepositoryJdbi();

        choicebox_boerderij.getItems().addAll(jdbiRepo.getBoerderijNamen());
        choicebox_klant.getItems().addAll(jdbiRepo.getKlantNamen());
        choicebox_jaar.getItems().addAll(makeYearList(2020, 2030));

        choicebox_size.setValue("large");
        choicebox_size.getItems().addAll(jdbiRepo.getSizes());
    }

    private void initTable() {
        tblInschrijving.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // tblBoerderijen.getColumns().clear();

        contract_nr.setCellValueFactory(new PropertyValueFactory<>("contract_nr"));
        boerderij.setCellValueFactory(new PropertyValueFactory<>("boerderij_naam"));
        boerderijID.setCellValueFactory(new PropertyValueFactory<>("boerderij_ID"));
        pakket.setCellValueFactory(new PropertyValueFactory<>("pakket_naam"));
        klant.setCellValueFactory(new PropertyValueFactory<>("klant_naam"));
        klantID.setCellValueFactory(new PropertyValueFactory<>("klant_ID"));
        jaar.setCellValueFactory(new PropertyValueFactory<>("contract_jaar"));

        tblInschrijving.setItems(getInschrijvingen());
    }

    private ObservableList<InschrijvingSub> getInschrijvingen() {
        var jdbiRepo = new RepositoryJdbi();
        return jdbiRepo.getInschrijvingenSub();
    }

    public ObservableList<String> makeYearList(Integer beginY, int endY) {
        ArrayList<String> yearList = new ArrayList<>();
        for (int i = beginY; i < endY; i++) {
            yearList.add(beginY.toString());
            beginY++;
        }
        return FXCollections.observableArrayList(yearList);
    }

    private void selectColumm() {
        InschrijvingSub selectedInschrijving = tblInschrijving.getSelectionModel().getSelectedItem();

        if (selectedInschrijving != null) {
            choicebox_boerderij.setValue(selectedInschrijving.getBoerderij_naam());
            choicebox_klant.setValue(selectedInschrijving.getKlant_naam());
            choicebox_size.setValue(selectedInschrijving.getPakket_naam());
            choicebox_jaar.setValue(selectedInschrijving.getContract_jaar());
        }
    }
}
