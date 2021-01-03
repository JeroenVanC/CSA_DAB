package be.kuleuven.csa.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BeheerActiviteitenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;

    @FXML
    private TableView<VerkooptSub> tblActiviteit;
    @FXML
    private TableColumn<Verkoopt, Integer> verkoopt_id;
    @FXML
    private TableColumn<Verkoopt, String> boerderij;
    @FXML
    private TableColumn<Verkoopt, Integer> boerderijID;
    @FXML
    private TableColumn<Verkoopt, String> pakket;
    @FXML
    private TableColumn<Verkoopt, Integer> prijs;

    @FXML
    private ChoiceBox<String> choicebox_size;
    @FXML
    private ChoiceBox<String> choicebox_boerderij;
    @FXML
    private TextField txtPrijs;


    public void initialize() {
        tblActiviteit.setOnMouseClicked(e -> selectColumm());
        initCB();
        initTable();
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

    private void deleteCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();
        VerkooptSub selectedVerkoopt = tblActiviteit.getSelectionModel().getSelectedItem();

        if (selectedVerkoopt != null) {
            jdbiRepo.deleteBoerderij(selectedVerkoopt.getVerkoopt_ID(), "VERKOOPT", "verkoopt_ID");
            initTable();
        }
        initTable();
        choicebox_boerderij.setValue(null);
        txtPrijs.clear();
        choicebox_size.setValue(null);
    }

    private void verifyOneRowSelected() {

    }

    private void modifyCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();

        Verkoopt selectedPrijzen = tblActiviteit.getSelectionModel().getSelectedItem();

        String sizeValue = choicebox_size.getValue();
        String prijsValue = txtPrijs.getText();
        String boerderijValue = choicebox_boerderij.getValue();

        if (selectedPrijzen == null) {
            showAlert("MAG NIET!", "TEXTFIELD Empty || NO ITEM Selected ");
        } else {

            jdbiRepo.updatePrijzen(boerderijValue, sizeValue, prijsValue, selectedPrijzen.getVerkoopt_ID());

        }

        initTable();
        choicebox_boerderij.setValue(null);
        txtPrijs.clear();
        choicebox_size.setValue(null);
    }

    private void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void addNewRow() {
        var jdbiRepo = new RepositoryJdbi();


        String sizeValue = choicebox_size.getValue();
        String prijsValue = txtPrijs.getText();
        String boerderijValue = choicebox_boerderij.getValue();

        if (choicebox_size.getValue().isBlank() || txtPrijs == null || choicebox_boerderij.getValue().isBlank()) {
            showAlert("MAG NIET!", "Vul iets in eh!!");
        } else {
            try {
                int n = Integer.valueOf(prijsValue);
                if (n < 1 || n > 1000) {
                    showAlert("MAG NIET", "niet te duur eh!");
                } else {
                    jdbiRepo.insertPrijzen(boerderijValue, sizeValue, prijsValue);
                }
            } catch (NumberFormatException nfe) {
                showAlert("MAG NIET!", "prijs moet een getal zijn");
            }
        }

        initTable();
        choicebox_boerderij.setValue(null);
        txtPrijs.clear();
        choicebox_size.setValue(null);
    }

    private void initCB() {
        var jdbiRepo = new RepositoryJdbi();

        choicebox_boerderij.getItems().addAll(jdbiRepo.getBoerderijNamen());

        choicebox_size.setValue("large");
        choicebox_size.getItems().addAll(jdbiRepo.getSizes());
    }

    private void selectColumm() {
        VerkooptSub selectedVerkoopt = tblActiviteit.getSelectionModel().getSelectedItem();

        if (selectedVerkoopt != null) {
            choicebox_size.setValue(selectedVerkoopt.getPakket_naam());
            txtPrijs.setText(selectedVerkoopt.getPrijs().toString());
            choicebox_boerderij.setValue(selectedVerkoopt.getBoerderij_naam());
        }
    }

    private void initTable() {
        tblActiviteit.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // tblBoerderijen.getColumns().clear();


        verkoopt_id.setCellValueFactory(new PropertyValueFactory<>("verkoopt_ID"));
        boerderij.setCellValueFactory(new PropertyValueFactory<>("boerderij_naam"));
        boerderijID.setCellValueFactory(new PropertyValueFactory<>("boerderij_ID"));
        pakket.setCellValueFactory(new PropertyValueFactory<>("pakket_naam"));
        prijs.setCellValueFactory(new PropertyValueFactory<>("prijs"));

        tblActiviteit.setItems(getActiviteiten());
    }

    private ObservableList<VerkooptSub> getActiviteiten() {
        var jdbiRepo = new RepositoryJdbi();
        return jdbiRepo.getVerkooptSub();
    }


}
