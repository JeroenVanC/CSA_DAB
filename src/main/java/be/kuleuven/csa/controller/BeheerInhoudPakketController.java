package be.kuleuven.csa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BeheerInhoudPakketController {
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;

    @FXML
    private TableView<InhoudPakketSub> tblInhoud;
    @FXML
    private TableColumn<InhoudPakketSub, String> productNaam;
    @FXML
    private TableColumn<InhoudPakketSub, String> pakketNaam;
    @FXML
    private TableColumn<InhoudPakketSub, Integer> aantal;
    @FXML
    private TableColumn<InhoudPakketSub, Integer> inWeek;
    @FXML
    private TableColumn<InhoudPakketSub, Integer> boerderij;


    @FXML
    private ChoiceBox<String> choicebox_size;
    @FXML
    private ChoiceBox<Integer> choicebox_aantal;
    @FXML
    private ChoiceBox<String> choicebox_product;
    @FXML
    private ChoiceBox<Integer> choicebox_week;
    @FXML
    private ChoiceBox<String> choicebox_boerderij;
    @FXML
    private ChoiceBox<Integer> choicebox_weekFilter;
    @FXML
    private TextField filterField;


    public void initialize() {
        tblInhoud.setOnMouseClicked(e -> selectColumm());
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
        InhoudPakketSub selectedAfhalingen = tblInhoud.getSelectionModel().getSelectedItem();
        int prodID = jdbiRepo.getProductID(selectedAfhalingen.getProduct_naam());
        int verkID = jdbiRepo.getVerkooptID(jdbiRepo.getBoerderijID(selectedAfhalingen.getBoerderij_naam()), selectedAfhalingen.getPakket_naam());


        if (selectedAfhalingen != null) {
            System.out.println("is niet null");
            jdbiRepo.deleteInhoudProd(prodID, "BEHOORT_TOT", "BEHOORT_TOT.product_ID", "BEHOORT_TOT.behoort_tot_week", selectedAfhalingen.getBehoort_tot_week(), verkID);
        }
        initTable();
        choicebox_size.setValue(null);
        choicebox_aantal.setValue(null);
        choicebox_product.setValue(null);
        choicebox_week.setValue(null);
        choicebox_boerderij.setValue(null);
    }

    private void verifyOneRowSelected() {
        if (tblInhoud.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een afhaling selecteren hé.");
        }
    }

    private void modifyCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();

        InhoudPakketSub selectedInhoud = tblInhoud.getSelectionModel().getSelectedItem();

        String sizeValue = choicebox_size.getValue();
        int aantalValue = choicebox_aantal.getValue();
        String productValue = choicebox_product.getValue();
        int weekValue = choicebox_week.getValue();
        String boerderijNaamValue = choicebox_boerderij.getValue();

        System.out.println(":" + selectedInhoud.getBoerderij_naam() + ":::" + boerderijNaamValue + ":");
        System.out.println(":" + selectedInhoud.getProduct_naam() + ":::" + productValue + ":");
        System.out.println(":" + selectedInhoud.getPakket_naam() + ":::" + sizeValue + ":");


        if (selectedInhoud == null) {
            showAlert("MAG NIET!", "TEXTFIELD Empty || NO ITEM Selected ");
        } else {
            if ((selectedInhoud.getBehoort_tot_week() != weekValue) || (selectedInhoud.getBehoort_tot_aantal() != aantalValue)) {
                if (selectedInhoud.getBoerderij_naam().equals(boerderijNaamValue) && selectedInhoud.getProduct_naam().equals(productValue) && selectedInhoud.getPakket_naam().equals(sizeValue)) {
                    jdbiRepo.updateInhoudPakket(boerderijNaamValue, sizeValue, productValue, weekValue, selectedInhoud.getBehoort_tot_week(), aantalValue, selectedInhoud.getBehoort_tot_aantal());
                } else {
                    showAlert("MAG NIET", "! You can only edit WEEK & AANTAL !");
                }
            } else {
                showAlert("MAG NIET", "! You can only edit WEEK & AANTAL !");
            }
        }
        initTable();
        choicebox_size.setValue(null);
        choicebox_aantal.setValue(null);
        choicebox_product.setValue(null);
        choicebox_week.setValue(null);
        choicebox_boerderij.setValue(null);
    }

    private void addNewRow() {
        var jdbiRepo = new RepositoryJdbi();


        String sizeValue = choicebox_size.getValue();
        int aantalValue = choicebox_aantal.getValue();
        String productValue = choicebox_product.getValue();
        int weekValue = choicebox_week.getValue();
        String boerderijNaamValue = choicebox_boerderij.getValue();

        if (choicebox_size.getValue().isBlank() || choicebox_aantal.getValue() == null || choicebox_product.getValue().isBlank() || choicebox_week.getValue() == null) {
            showAlert("MAG NIET!", "Vul iets in eh!!");
        } else {
            jdbiRepo.insertInhoudPakket(productValue, sizeValue, weekValue, boerderijNaamValue, aantalValue);
        }

        initTable();
        choicebox_size.setValue(null);
        choicebox_aantal.setValue(null);
        choicebox_product.setValue(null);
        choicebox_week.setValue(null);
        choicebox_boerderij.setValue(null);
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

        choicebox_week.getItems().addAll(makeWeekList(1, 53));
        choicebox_aantal.getItems().addAll(makeWeekList(1, 53));
        choicebox_product.getItems().addAll(jdbiRepo.getProfuctenNaam());
        choicebox_boerderij.getItems().addAll(jdbiRepo.getBoerderijNamen());
        choicebox_weekFilter.getItems().addAll(makeWeekList(1, 53));

        choicebox_size.setValue("large");
        choicebox_size.getItems().addAll(jdbiRepo.getSizes());
    }

    private void initTable() {
        tblInhoud.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        productNaam.setCellValueFactory(new PropertyValueFactory<>("product_naam"));
        pakketNaam.setCellValueFactory(new PropertyValueFactory<>("pakket_naam"));
        aantal.setCellValueFactory(new PropertyValueFactory<>("behoort_tot_aantal"));
        inWeek.setCellValueFactory(new PropertyValueFactory<>("behoort_tot_week"));
        boerderij.setCellValueFactory(new PropertyValueFactory<>("boerderij_naam"));

        FilteredList<InhoudPakketSub> filteredList = new FilteredList<>(getActiviteiten(), p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(inhoudPakket -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (inhoudPakket.getPakket_naam().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches pakket naam.
                } else if (inhoudPakket.getProduct_naam().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches product naam.
                } else if (inhoudPakket.getBehoort_tot_week().toString().contains(lowerCaseFilter)){
                    return true; // Filter matches week
                } else if (inhoudPakket.getBoerderij_naam().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches boerderij naam
                }
                return false; // Does not match.
            });

        });

        choicebox_weekFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(inhoudPakket -> {
                if (newValue == null) {
                    return true;
                }

                if (inhoudPakket.getBehoort_tot_week() == newValue) {
                    return true;
                }
                return false;
            });
        });

        SortedList<InhoudPakketSub> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblInhoud.comparatorProperty());

        tblInhoud.setItems(sortedList);
    }

    private ObservableList<InhoudPakketSub> getActiviteiten() {
        var jdbiRepo = new RepositoryJdbi();
        return jdbiRepo.getInhoudPakketSub();
    }

    private void selectColumm() {
        InhoudPakketSub selectedInhoud = tblInhoud.getSelectionModel().getSelectedItem();

        if (selectedInhoud != null) {
            choicebox_size.setValue(selectedInhoud.getPakket_naam());
            choicebox_aantal.setValue(selectedInhoud.getBehoort_tot_aantal());
            choicebox_product.setValue(selectedInhoud.getProduct_naam());
            choicebox_week.setValue(selectedInhoud.getBehoort_tot_week());
            choicebox_boerderij.setValue(selectedInhoud.getBoerderij_naam());
        }
    }

    public ObservableList<Integer> makeWeekList(Integer beginY, int endY) {
        ArrayList<Integer> weekList = new ArrayList<>();
        for (int i = beginY; i < endY; i++) {
            weekList.add(beginY);
            beginY++;
        }
        return FXCollections.observableArrayList(weekList);
    }
}
