package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.models.Message;
import com.esprit.services.GroupeService;
import com.esprit.services.MessageService;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class ChatboxController {
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
            chatTextArea.appendText(message.getIdGroupe() + ": " + message.getText() + "\n");
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
            chatTextArea.appendText("User: " + messageContent + "\n");

            // Clear the messageTextField
            messageTextField.clear();
        }
    }
}
