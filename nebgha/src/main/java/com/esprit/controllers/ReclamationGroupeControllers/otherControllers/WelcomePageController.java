package com.esprit.controllers.ReclamationGroupeControllers.otherControllers;

import com.esprit.services.ReclamationGroupServices.Session;
import com.esprit.services.ReclamationGroupServices.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomePageController {
    @FXML
    public AnchorPane A;
    public Button bt;
    public TextField tf;
    public TextField tfidUser;
    public Label lab;
    public TextField t;

    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;
    UtilisateurService us =new UtilisateurService();

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isNull(Object obj) {
        return obj == null;
    }
    public void setIdUser() throws IOException {

        if( tfidUser.getText().isEmpty()){
             lab.setText("Le champ id est vide");
        }else if(!isInteger(tfidUser.getText())){
            lab.setText("L'id doit etre numerique ");
            /*Utilisateur u = us.rechercheUtilisateur(Integer.parseInt(tfidUser.getText()));
            if(isNull(u)) {
            lab.setText("Utilisateur non trouvé ");
                System.out.println(u);
            }*/
        }else{
            Session.login(Integer.parseInt(tfidUser.getText()));
            ss.SwitchScene2(event,"testFlow",tfidUser);
        }


    }

    public void SwitchToAfficherMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherMessage",tf);
    }

    public void SwitchToAfficherGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherGroupe",tf);
    }

    public void SwitchToAfficherReclamation(ActionEvent actionEvent ) throws IOException {
       ss.SwitchScene2(event,"AfficherReclamation",tf);

    }

    public void SwitchTowelcomeAdmin(ActionEvent actionEvent ) throws IOException {
        ss.SwitchScene2(event,"WelcomeAdmin",t);

    }



}
