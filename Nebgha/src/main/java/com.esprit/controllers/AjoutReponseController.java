package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AjoutReponseController implements Initializable {

    @FXML
    private CheckBox est_correcte;

    @FXML
    private TextField explicationtf;

    @FXML
    private TextField ordretf;

    @FXML
    private ComboBox<String> questionList;


    @FXML
    private TextField texttf;

    private ReponsesService reponseService = new ReponsesService();

    @FXML
    private Questions selectedQuestion;

    @FXML
    void addReponse(ActionEvent event) throws IOException {
        ReponsesService qs = new ReponsesService();
        String selectedQuestionName = questionList.getValue();

        if (selectedQuestion != null) {
            Reponses reponse = new Reponses(selectedQuestion, texttf.getText(),
                    est_correcte.isSelected(), Integer.parseInt(ordretf.getText()),
                    explicationtf.getText()
            );

            reponseService.ajouter(reponse);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réponse ajoutée");
            alert.setContentText("Réponse ajoutée!");
            alert.showAndWait();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowReponses.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Réponses");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une question");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuestionsService questionsService = new QuestionsService();
        List<Questions> questions = questionsService.afficher();

        List<String> questionTexts = questions.stream().map(Questions::getTexte).collect(Collectors.toList());
        questionList.setItems(FXCollections.observableArrayList(questionTexts));

        questionList.setOnAction(event -> {
            String selectedQuestionName = questionList.getValue();
            selectedQuestion = questions.stream().filter(q -> q.getTexte().equals(selectedQuestionName)).findFirst().orElse(null);
        });
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowReponses.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Réponses");
        stage.show();
    }

}
