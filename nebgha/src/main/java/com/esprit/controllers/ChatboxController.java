package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.models.Message;
import com.esprit.services.MessageService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class ChatboxController {
    public VBox vbox;
    @FXML
    public ImageView sendButtonI;
    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendButton;

    private Groupe selectedGroup;

    MessageService ms =new MessageService();


    public void initData(Groupe selectedGroupe) {
        this.selectedGroup = selectedGroupe;
        // Initialize chat history by fetching messages from the database for the selected group
        List<Message> chatHistory = ms.readByid(selectedGroupe.getId_groupe());
        displayChatHistory(chatHistory);
    }

    private void displayChatHistory(List<Message> chatHistory) {
        for (Message message : chatHistory) {
           // chatTextArea.appendText(message.getIdGroupe() + ": " + message.getText() + "\n");
            Label messageLabel = new Label(message.getIdGroupe() + ": " + message.getText());
            messageLabel.setStyle("-fx-background-color: #4B87F6; -fx-background-radius: 40; -fx-font-size: 20; -fx-text-fill: white ");
            messageLabel.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setLineSpacing(10);
            HBox hbox =new HBox(messageLabel);
            hbox.setAlignment(Pos.CENTER_RIGHT);
            vbox.setSpacing(20);
            vbox.getChildren().add(hbox);

        }
    }

    /*@FXML
    private void handleSendButtonAction(ActionEvent event) {
        String messageContent = messageTextField.getText().trim();
        if (!messageContent.isEmpty()) {
            // Save the message to the database
            Message newMessage = new Message(selectedGroup.getId_groupe(), "User", messageContent);
            ms.ajouter(newMessage);

            // Update the chatTextArea with the new message
            chatTextArea.appendText("User: " + messageContent + "\n");

            // Clear the messageTextField
            messageTextField.clear();
        }
    }*/

    @FXML
    public void handleSendButtonAction(javafx.event.ActionEvent actionEvent) {
        String messageContent = messageTextField.getText().trim();
        if (!messageContent.isEmpty()) {
            // Save the message to the database
            Message newMessage = new Message(selectedGroup.getId_groupe(), String.valueOf(LocalDate.now()), messageContent);
            ms.ajouter(newMessage);
            // Update the chatTextArea with the new message
            Label newM = new Label("User: " + messageContent + "\n");
            newM.setStyle("-fx-background-color: #4B87F6; -fx-background-radius: 40; -fx-font-size: 20; -fx-text-fill: #FF0000; ");
            //chatTextArea.appendText(String.valueOf(newM));
            vbox.getChildren().add(newM);

            // Clear the messageTextField
            messageTextField.clear();
        }
    }

    public void handleSendButtonActionI(MouseEvent mouseEvent) {
        String messageContent = messageTextField.getText().trim();
        if (!messageContent.isEmpty()) {
            // Save the message to the database
            Message newMessage = new Message(selectedGroup.getId_groupe(), String.valueOf(LocalDate.now()), messageContent);
            ms.ajouter(newMessage);

            Label  newM =new Label("User: " + messageContent + "\n");
            // Update the chatTextArea with the new message
            vbox.getChildren().add(newM);

            // Clear the messageTextField
            messageTextField.clear();
        }
    }
}
