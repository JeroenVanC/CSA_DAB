package be.kuleuven.csa.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.lightcouch.CouchDbClient;

import java.awt.*;
import java.net.URI;

public class BeheerTipsController {


    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;

    @FXML
    private TableView<Tips> tblTips;
    @FXML
    private TableColumn<Tips, String> product_naam;
    @FXML
    private TableColumn<Tips, String> tip;
    @FXML
    private TableColumn<Tips, String> auteur;

    @FXML
    private ChoiceBox<String> choicebox_product;
    @FXML
    private ChoiceBox<String> choicebox_auteur;
    @FXML
    private TextField txtfldTip;

    CouchDbClient cdbc;
    RepositoryCouch repoC;

    public BeheerTipsController() {
        try {
            cdbc = new CouchDbClient();
            repoC = new RepositoryCouch(cdbc);
        } catch (Exception e) {
            showAlert("Error", "Can not connect to CouchDB. Is it running? / Are you logged in correctly?");
        }
    }

    public void initialize() {

        initTable();
        initCB();
        tblTips.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && tblTips.getSelectionModel().getSelectedItem() != null) {
                var selectedRow = tblTips.getSelectionModel().getSelectedItem();
                runLink(selectedRow.getTip());
            }
            selectColumm();
        });
        btnClose.setOnAction(e -> {
            cdbc.shutdown();
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
        btnAdd.setOnAction(e -> addNewRow());
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
        });

    }

    private void deleteCurrentRow() {
        Tips selectedTip = tblTips.getSelectionModel().getSelectedItem();
        repoC.deleteTip(selectedTip);
        initTable();
        choicebox_auteur.setValue(null);
        choicebox_product.setValue(null);
        txtfldTip.clear();
    }

    private void verifyOneRowSelected() {
        if (tblTips.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een tip selecteren hé.");
        }
    }

    private void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void addNewRow() {
        String productValue = choicebox_product.getValue();
        String tipValue = txtfldTip.getText();
        String auteurValue = choicebox_auteur.getValue();
        repoC.insertTips(productValue, tipValue, auteurValue);
        initTable();
        choicebox_auteur.setValue(null);
        choicebox_product.setValue(null);
        txtfldTip.clear();
    }

    private void initCB() {
        var jdbiRepo = new RepositoryJdbi();
        choicebox_product.getItems().addAll(jdbiRepo.getProfuctenNaam());
        choicebox_auteur.getItems().addAll(jdbiRepo.getBoerderijNamen());
        choicebox_auteur.getItems().addAll(jdbiRepo.getKlantNamen());
    }

    private void selectColumm() {
        Tips selectedTips = tblTips.getSelectionModel().getSelectedItem();
        if (selectedTips != null) {
            choicebox_product.setValue(selectedTips.getProduct_naam());
            txtfldTip.setText(selectedTips.getTip());
            choicebox_auteur.setValue(selectedTips.getAuteur());
        }
    }

    private void runLink(String link) {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI(link);
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTable() {
        tblTips.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        product_naam.setCellValueFactory(new PropertyValueFactory<>("product_naam"));
        tip.setCellValueFactory(new PropertyValueFactory<>("tip"));
        auteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));

        tblTips.setItems(getTips());

    }

    private ObservableList<Tips> getTips() {

        return repoC.getTips();
    }
}
