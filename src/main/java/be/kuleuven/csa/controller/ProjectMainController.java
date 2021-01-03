package be.kuleuven.csa.controller;

import be.kuleuven.csa.ProjectMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProjectMainController {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button btnBoerderijen;
    @FXML
    private Button btnTips;
    @FXML
    private Button btnKlanten;
    @FXML
    private Button btnInschrijvingen;
    @FXML
    private Button btnProducten;
    @FXML
    private Button btnActiviteiten;
    @FXML
    private Button btnAfhaling;
    @FXML
    private Button btnInhoud;
    @FXML
    private Button btnPakket;

    public void initialize() {

        btnBoerderijen.setOnAction(e -> showBeheerScherm("boerderijen"));
        btnTips.setOnAction(e -> showBeheerScherm("tips"));
        btnKlanten.setOnAction(e -> showBeheerScherm("klanten"));
        btnInschrijvingen.setOnAction(e -> showBeheerScherm("inschrijvingen"));
        btnProducten.setOnAction(e -> showBeheerScherm("producten"));
        btnActiviteiten.setOnAction(e -> showBeheerScherm("prijzen"));
        btnAfhaling.setOnAction(e -> showBeheerScherm("afhaling"));
        btnInhoud.setOnAction(e -> showBeheerScherm("inhoudpakket"));
        btnPakket.setOnAction(e -> showBeheerScherm("pakket"));

    }

    private void showBeheerScherm(String id) {
        var resourceName = "beheer" + id + ".fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Beheer van " + id);
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }
}
