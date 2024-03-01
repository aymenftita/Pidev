package com.esprit.controllers.messageControllers;

import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Groupe;
import com.esprit.models.Message;
import com.esprit.models.Utilisateur;
import com.esprit.services.GroupeService;
import com.esprit.services.MessageService;
import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.awt.Color.white;

public class ChatBoxController implements Initializable {


    public TextField M;
    public TextFlow flowM;
    public FlowPane flowG;

    GroupeService gs = new GroupeService();

    private List<Groupe> allGroupes = gs.afficher();
    MessageService ms = new MessageService();

    private List<Message> allMessages = ms.afficher();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        showMessages(allMessages);
        showUserGroups(allGroupes);
    }

    private void showMessages(List<Message> messages) {
        flowM.getChildren().clear();
        for (Message message : messages) {

            Separator seperator = new Separator(Orientation.HORIZONTAL);
            seperator.setStyle("-fx-arc-width: 200; -fx-background-color: blue");
            VBox quizBlock = createMessageBlock(message);
            flowM.setLineSpacing(20);
            flowM.getChildren().addAll(seperator,quizBlock);

        }
    }

    private VBox createMessageBlock(Message message) {

        VBox quizBlock = new VBox();
        //quizBlock.setStyle("-fx-background-color: #EFF4FC; -fx-border-color: #000066;  -fx-border-radius: 5;-fx-padding: 20; -fx-margin:100; -fx-spacing: 50");
        quizBlock.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(message.getText());


        nameLabel.setStyle("-fx-background-radius: 20;-fx-background-color: white");

        String[] lines = nameLabel.getText().split("\n");
        for (String line : lines) {
            Text textNode = new Text(line);
            textNode.setStyle("-fx-background-radius: 20;-fx-background-color: blue");
            flowM.getChildren().add(textNode);
            flowM.getChildren().add(new Text("\n")); // Adding a newline after each line
        }


        //quizBlock.getChildren().addAll(nameLabel);
        return quizBlock;
    }




    private void showUserGroups(List<Groupe> groupes) {
        flowG.getChildren().clear();
        for (Groupe groupe : groupes) {

            Separator seperator = new Separator(Orientation.VERTICAL);
            seperator.setStyle("-fx-arc-width: 200");
            VBox quizBlock = createGroupeBlock(groupe);
            flowG.getChildren().addAll(quizBlock,seperator);

        }
    }
    private VBox createGroupeBlock(Groupe groupe) {

        VBox quizBlock = new VBox(20);
        quizBlock.setSpacing(20);
        quizBlock.setStyle("-fx-background-color: #EFF4FC; -fx-border-color: #000066;  -fx-border-radius: 5;-fx-padding: 20; -fx-margin:100; -fx-spacing: 50");
        quizBlock.setAlignment(Pos.CENTER);


        Button btGroupe = new Button(groupe.getTitre());


        quizBlock.getChildren().addAll(btGroupe);
        return quizBlock;
    }


    public void sendMessage(ActionEvent actionEvent) throws Exception {

        try {
            MessageService ms = new MessageService();
            ms.ajouter(new Message(20,"2024-02-15",M.getText()));
            showMessages(allMessages);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/ChatBox.fxml"));
            Parent root = loader.load();
            M.getScene().setRoot(root);
        }catch (Exception e){
            System.out.println("************************************");
            System.out.println(e.getMessage());
        }

    }
}
