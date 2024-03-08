package com.esprit.controllers.cours;

import com.esprit.models.cours.lecon;
import com.esprit.services.cours.GPTService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class InterfaceEtudiantLeconController {

    @FXML
    private TableView<lecon> leconsTable;

    @FXML
    private TableColumn<lecon, String> titreColumn;

    @FXML
    private TableColumn<lecon, String> descriptionColumn;

    @FXML
    private TableColumn<lecon, String> contenuColumn;

    @FXML
    private Button continuerButton;

    @FXML
    private Button previousButton;

    @FXML
    private ImageView updateImageView;

    @FXML
    private Button bGPT;

    @FXML
    private TextField tfGPT1;

    @FXML
    private TextField tfGPT2;

    @FXML
    private Label lbA;
    @FXML
    private void initialize() {

    }

    @FXML
    void continuer(ActionEvent event) {
        // Code pour changer de scène ou gérer la continuité vers la leçon suivante
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NouvelleScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) continuerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        // Code pour revenir à la scène précédente
        Stage stage = (Stage) previousButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateInterface(ActionEvent event) {
        // Code pour mettre à jour l'interface (rafraîchir les données, etc.)
        leconsTable.getItems().clear();
        leconsTable.getItems().addAll();

    }

    public void handleRequest(ActionEvent actionEvent) throws IOException, InterruptedException {
        GPTService gpt = new GPTService();
        lbA.setText(gpt.request(tfGPT1.getText(),tfGPT2.getText()));
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceEtudiant.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
    @FXML
    void openVideo(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/VideoReader.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
}

