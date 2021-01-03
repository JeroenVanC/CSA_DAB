package be.kuleuven.csa.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class BeheerProductenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;

    @FXML
    private TableView<Product> tblProducten;

    @FXML
    public TableColumn<Product, Integer> product_id;
    @FXML
    public TableColumn<Product, String> product_naam;
    @FXML
    public TableColumn<Product, Integer> product_normal;
    @FXML
    public TableColumn<Product, String> product_tip;
    @FXML
    public TableColumn<Product, String> product_tip_auteur;

    @FXML
    public TextField textfieldTip;
    @FXML
    public TextField textfieldNaam;
    @FXML
    public TextField textfieldAuthor;
    @FXML
    public CheckBox checkboxNormal;


    public void initialize() {
        tblProducten.setOnMouseClicked(e -> selectColumm());
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
        tblProducten.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // tblBoerderijen.getColumns().clear();

        product_id.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        product_naam.setCellValueFactory(new PropertyValueFactory<>("product_naam"));
        product_normal.setCellValueFactory(new PropertyValueFactory<>("product_normaal"));

        tblProducten.setItems(getProducten());
    }

    private ObservableList<Product> getProducten() {
        var jdbiRepo = new RepositoryJdbi();
        return jdbiRepo.getProducten();
    }

    private void deleteCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();
        Product selectedProduct = tblProducten.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            jdbiRepo.deleteBoerderij(selectedProduct.getProduct_id(), "PRODUCT", "product_ID");
            initTable();
        }
        initTable();
        textfieldTip.clear();
        textfieldAuthor.clear();
        textfieldNaam.clear();
        checkboxNormal.setSelected(false);
    }


    private void modifyCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();

        Product selectedProduct = tblProducten.getSelectionModel().getSelectedItem();

        String naam = textfieldNaam.getText();
        int value = 0;
        if (checkboxNormal.isSelected()) {
            value = 1;
        }

        if (textfieldNaam.getText().isBlank() || selectedProduct == null) {
            showAlert("MAG NIET!", "TEXTFIELD Empty || NO ITEM Selected ");
        } else {

            jdbiRepo.updateProduct(naam, selectedProduct.getProduct_id(), value);

        }
        initTable();
        textfieldNaam.clear();
        checkboxNormal.setSelected(false);
    }

    private void addNewRow() {
        var jdbiRepo = new RepositoryJdbi();

        String naam = textfieldNaam.getText();
        int normal = 0;
        boolean cb = checkboxNormal.isSelected();

        if (cb) {
            normal = 1;
        }

        if (textfieldNaam.getText().isBlank()) {
            showAlert("MAG NIET!", "Vul iets in eh!!");
        } else {
            jdbiRepo.insertProduct(naam, normal);
        }
        initTable();
        textfieldNaam.clear();
        checkboxNormal.setSelected(false);
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if (tblProducten.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een boer selecteren hé.");
        }
    }

    private void selectColumm() {
        boolean normaal = false;

        Product selectedProduct = tblProducten.getSelectionModel().getSelectedItem();
        System.out.println(selectedProduct.getProduct_normaal());
        if (selectedProduct.getProduct_normaal() == 1) {
            normaal = true;
        }

        if (selectedProduct != null) {
            textfieldNaam.setText(selectedProduct.getProduct_naam());

            checkboxNormal.setSelected(normaal);

        }
    }


}
