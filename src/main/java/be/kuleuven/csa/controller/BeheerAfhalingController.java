package be.kuleuven.csa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BeheerAfhalingController {
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;

    @FXML
    private TableView<AfhalingSub> tblAfhaling;
    @FXML
    private TableColumn<AfhalingSub, String> klantNaam;
    @FXML
    private TableColumn<AfhalingSub, String> boerderijNaam;
    @FXML
    private TableColumn<AfhalingSub, String> pakketNaam;
    @FXML
    private TableColumn<AfhalingSub, Integer> inWeek;


    @FXML
    private ChoiceBox<String> choicebox_size;
    @FXML
    private ChoiceBox<String> choicebox_klant;
    @FXML
    private ChoiceBox<String> choicebox_boerderij;
    @FXML
    private ChoiceBox<Integer> choicebox_week;


    public void initialize() {
        tblAfhaling.setOnMouseClicked(e -> selectColumm());
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

    private void initCB() {
        var jdbiRepo = new RepositoryJdbi();

        choicebox_klant.getItems().addAll(jdbiRepo.getKlantNamen());
        choicebox_boerderij.getItems().addAll(jdbiRepo.getBoerderijNamen());
        choicebox_week.getItems().addAll(makeWeekList(1, 53));

        choicebox_size.setValue("large");
        choicebox_size.getItems().addAll(jdbiRepo.getSizes());
    }

    private void deleteCurrentRow() {

        var jdbiRepo = new RepositoryJdbi();
        AfhalingSub selectedAfhalingen = tblAfhaling.getSelectionModel().getSelectedItem();
        int klant_id = jdbiRepo.getKlantID(selectedAfhalingen.getKlant_naam());

        System.out.println(selectedAfhalingen.getKlant_ID());


        if (selectedAfhalingen != null) {
            System.out.print("is niet null");
            jdbiRepo.deleteAfhaling(klant_id, "HAALT_AF", "HAALT_AF.klant_ID", "HAALT_AF.haalt_af_week", selectedAfhalingen.getHaalt_af_week());
            initTable();
        }
        initTable();
        choicebox_klant.setValue(null);
        choicebox_boerderij.setValue(null);
        choicebox_week.setValue(null);
        choicebox_size.setValue(null);
    }

    private void verifyOneRowSelected() {
        if (tblAfhaling.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Hela!", "Eerst een afhaling selecteren hé.");
        }
    }

    private void modifyCurrentRow() {
        var jdbiRepo = new RepositoryJdbi();

        AfhalingSub selectedAfhalingen = tblAfhaling.getSelectionModel().getSelectedItem();

        String klantValue = choicebox_klant.getValue();
        String boerderijValue = choicebox_boerderij.getValue();
        String sizeValue = choicebox_size.getValue();
        int weekValue = choicebox_week.getValue();


        if (selectedAfhalingen == null) {
            showAlert("MAG NIET!", "TEXTFIELD Empty || NO ITEM Selected ");
        } else {
            if (selectedAfhalingen.getBoerderij_naam().equals(boerderijValue) && selectedAfhalingen.getKlant_naam().equals(klantValue) && selectedAfhalingen.getPakket_naam().equals(sizeValue) && weekValue != selectedAfhalingen.getHaalt_af_week()) {
                jdbiRepo.updateAfhalingen(boerderijValue, sizeValue, klantValue, weekValue, selectedAfhalingen.getHaalt_af_week());
            } else {
                showAlert("MAG NIET", "! You can only edit WEEK !");
            }
        }

        initTable();
        choicebox_klant.setValue(null);
        choicebox_boerderij.setValue(null);
        choicebox_size.setValue(null);
        choicebox_week.setValue(null);
    }

    private void addNewRow() {
        var jdbiRepo = new RepositoryJdbi();


        String klantValue = choicebox_klant.getValue();
        String boerderijValue = choicebox_boerderij.getValue();
        String sizeValue = choicebox_size.getValue();
        int weekValue = choicebox_week.getValue();

        if (choicebox_klant.getValue().isBlank() || choicebox_boerderij.getValue().isBlank() || choicebox_size.getValue().isBlank()) {
            showAlert("MAG NIET!", "Vul iets in eh!!");
        } else {
            jdbiRepo.insertAfhalingen(klantValue, boerderijValue, sizeValue, weekValue);
        }

        initTable();
        choicebox_klant.setValue(null);
        choicebox_boerderij.setValue(null);
        choicebox_size.setValue(null);
        choicebox_week.setValue(null);
    }

    private void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void initTable() {
        tblAfhaling.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // tblBoerderijen.getColumns().clear();


        klantNaam.setCellValueFactory(new PropertyValueFactory<>("klant_naam"));
        boerderijNaam.setCellValueFactory(new PropertyValueFactory<>("boerderij_naam"));
        pakketNaam.setCellValueFactory(new PropertyValueFactory<>("pakket_naam"));
        inWeek.setCellValueFactory(new PropertyValueFactory<>("haalt_af_week"));

        tblAfhaling.setItems(getActiviteiten());
    }

    private ObservableList<AfhalingSub> getActiviteiten() {
        var jdbiRepo = new RepositoryJdbi();
        return jdbiRepo.getAhalingSub();
    }

    private void selectColumm() {
        AfhalingSub selectedAfhaling = tblAfhaling.getSelectionModel().getSelectedItem();

        if (selectedAfhaling != null) {
            choicebox_klant.setValue(selectedAfhaling.getKlant_naam());
            choicebox_boerderij.setValue(selectedAfhaling.getBoerderij_naam());
            choicebox_size.setValue(selectedAfhaling.getPakket_naam());
            choicebox_week.setValue(selectedAfhaling.getHaalt_af_week());
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
