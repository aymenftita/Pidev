package com.esprit.controllers.reponse;

import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Question;
import com.esprit.models.Reponse;
import com.esprit.models.Sujet;
import com.esprit.services.questionService;
import com.esprit.services.reponseService;
import com.esprit.services.sujetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AjoutReponseController {
    @FXML
    private DatePicker dpDateReponse;

    @FXML
    private TextField tfAuteurID;

    @FXML
    private TextArea tfContenuReponse;

    @FXML
    private ComboBox<Question> cbChoixQuestion;

    @FXML
    private ComboBox<Sujet> cbChoixSujet;

    @FXML
    void initialize() {
        sujetService ss = new sujetService();
        questionService qs = new questionService();
        List<Sujet> sujets = ss.afficher();
        cbChoixSujet.getItems().setAll(sujets);

        // Configuer cell factory pour cbChoixSujet pour afficher seulement le titre
        cbChoixSujet.setCellFactory(listView -> new ListCell<Sujet>() {
            @Override
            protected void updateItem(Sujet sujet, boolean empty) {
                super.updateItem(sujet, empty);
                if (sujet != null) {
                    setText(sujet.getTitre());
                } else {
                    setText(null);
                }
            }
        });

        List<Question> questions = qs.afficher();
        cbChoixQuestion.getItems().setAll(questions);
        // Configuer cell factory pour cbChoixQuestion pour afficher seulement le titre
        cbChoixQuestion.setCellFactory(listView -> new ListCell<Question>() {
            @Override
            protected void updateItem(Question question, boolean empty) {
                super.updateItem(question, empty);
                if (question != null) {
                    setText(question.getTitre());
                } else {
                    setText(null);
                }
            }
        });
    }

    @FXML
    void ajouterReponse(ActionEvent event) {
        Question selectedQuestion = cbChoixQuestion.getValue();
        Sujet selectedSujet = cbChoixSujet.getValue();

        //Création du service et ajout d'entité
        reponseService rs = new reponseService();
        rs.ajouter(new Reponse(0, Integer.parseInt(tfAuteurID.getText()),
                selectedQuestion, tfContenuReponse.getText(),
                Date.valueOf(dpDateReponse.getValue()), selectedSujet));

        //Message de confirmation
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Réponse");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("Réponse ajouté!");
        alertAjout.show();

    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfContenuReponse.getScene().setRoot(root);
    }

}
