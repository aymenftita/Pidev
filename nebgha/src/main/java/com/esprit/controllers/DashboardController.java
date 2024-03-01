package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.services.GroupeService;
import com.esprit.services.MessageService;
import com.esprit.services.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.esprit.services.Session.userId;

public class DashboardController implements Initializable {
    @FXML
    private ComboBox<Groupe> groupComboBox;

    GroupeService gs =new GroupeService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch user groups from the database based on user ID
        List<Groupe> userGroups = gs.afficherG(Session.getUserId());

        // Populate the ComboBox with user groups
        groupComboBox.getItems().addAll(userGroups);
    }

   /* @FXML
    private void handleComboBoxAction(ActionEvent event) {
        Groupe selectedGroup = groupComboBox.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            openChatbox(selectedGroup);
        }
    }*/

    private void openChatbox(Groupe selectedGroup) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/chat.fxml"));
            Parent root = loader.load();

            ChatboxController chatboxController = loader.getController();
            chatboxController.initData(selectedGroup);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(selectedGroup.getTitre() + " Chat");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogoutButtonAction(javafx.event.ActionEvent actionEvent) {
        Groupe selectedGroup = groupComboBox.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            openChatbox(selectedGroup);
            System.out.println(selectedGroup);
        }
    }
}
