package com.esprit.controllers.reclamationControllers;
import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Reclamation;
import com.esprit.models.Status;
import com.esprit.models.Utilisateur;
import com.esprit.services.ReclamationService;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


public class AjouterReclamationController {



    @FXML
    public TextField tfP;
    public Label csSujet;
    public Label csDesc;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tfSujet;

    @FXML
    private TextField tfDescription;
    @FXML
    private DatePicker tfDate;
    UtilisateurService us = new UtilisateurService();
    Utilisateur u = us.rechercheUtilisateur(Session.getUserId());


    @FXML
    void addReclamation(ActionEvent event) throws IOException {

        if(tfSujet.getText().trim().isEmpty() ) {
            csSujet.setText("Le champ Sujet et vide");
            csDesc.setText("");
        }else if(tfDescription.getText().trim().isEmpty()){
            csDesc.setText("Le champ Description et vide");
            csSujet.setText("");
        }else {
            ReclamationService ps = new ReclamationService();
            ps.ajouter(new Reclamation(
                    u,
                    String.valueOf(LocalDate.now()),
                    String.valueOf(tfSujet.getText()),
                    String.valueOf(tfDescription.getText()),
                    "Envoyée",
                    1,"admin" ));
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Personne ajoutée");
            alert.setContentText("Personne ajoutée !");
            alert.show();*/
            SwitchScenesController ss = new SwitchScenesController();
            ss.SwitchScene2(event,"AfficherReclamationUser",tfSujet);
        }


    }

    public void initialize(){


        /*cb.setItems(FXCollections.observableArrayList(
                Status.envoyé.toString(),
                Status.EnCourDeTraitement.toString(),
                Status.Traité.toString(),
                Status.EnReponse.toString()));

        cbUsers.setItems(FXCollections.observableArrayList(users));
        cbUsers.setCellFactory(listView -> new ListCell<Utilisateur>() {
            @Override
            protected void updateItem(Utilisateur user, boolean empty) {
                super.updateItem(user, empty);
                if (user != null) {
                    setText(String.valueOf(user.getNom()));
                } else {
                    setText(null);
                }
            }
        });*/
    }






}
