package be.kuleuven.csa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BeheerPakketController {

    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;

    @FXML
    private TableView<Pakket> tblPakket;

    @FXML
    public TableColumn<Boerderij, Integer> pakket;
    @FXML
    public TableColumn<Boerderij, String> volwassenen;
    @FXML
    public TableColumn<Boerderij, String> kinderen;

    @FXML
    private ChoiceBox<Integer> choicebox_vol;
    @FXML
    private ChoiceBox<Integer> choicebox_kind;


    public void initialize() {

        tblPakket.setOnMouseClicked(e -> selectColumm());
        initTable();
        initCB();

        btnModify.setOnAction(e -> {
            modifyCurrentRow();
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private void initCB() {
        choicebox_kind.getItems().addAll(makeWeekList(0, 11));
        choicebox_vol.getItems().addAll(makeWeekList(0, 11));
    }

    private void deleteCurrentRow() {

    }

    private void verifyOneRowSelected() {

    }

    private void modifyCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();

        Pakket selectedInhoud = tblPakket.getSelectionModel().getSelectedItem();




        if (selectedInhoud == null) {
            showAlert("MAG NIET!", "TEXTFIELD Empty || NO ITEM Selected ");
        } else {
            int volwassenen = choicebox_vol.getValue();
            int kinderen = choicebox_kind.getValue();
            jdbiRepo.updatePakket(selectedInhoud.getPakket_naam(), volwassenen, kinderen);
        }
        initTable();
        choicebox_vol.setValue(null);
        choicebox_kind.setValue(null);
    }

    private void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void addNewRow() {

    }

    private void initTable() {
        tblPakket.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        pakket.setCellValueFactory(new PropertyValueFactory<>("pakket_naam"));
        volwassenen.setCellValueFactory(new PropertyValueFactory<>("pakket_volwassenen"));
        kinderen.setCellValueFactory(new PropertyValueFactory<>("pakket_kinderen"));

        tblPakket.setItems(getPakket());
    }


    private ObservableList<Pakket> getPakket() {
        var jdbiRepo = new RepositoryJdbi();
        return jdbiRepo.getPakket();
    }


    private void selectColumm() {
        Pakket selectedPakket = tblPakket.getSelectionModel().getSelectedItem();
        if (selectedPakket != null) {
            choicebox_kind.setValue(selectedPakket.getPakket_kinderen());
            choicebox_vol.setValue(selectedPakket.getPakket_volwassenen());
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
