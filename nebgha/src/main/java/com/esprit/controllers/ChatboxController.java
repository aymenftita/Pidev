package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.models.Message;
import com.esprit.services.MessageService;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;


public class ChatboxController {
    public VBox vbox;
    @FXML
    public ImageView sendButtonI;
    public Label typing;
    public ScrollPane scroll;
    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendButton;

    private Groupe selectedGroup;

    MessageService ms = new MessageService();

    UtilisateurService us = new UtilisateurService();

    public void initialize() {


    }

    public void initData(Groupe selectedGroupe) {
        this.selectedGroup = selectedGroupe;
        // Initialize chat history by fetching messages from the database for the selected group
        List<Message> chatHistory = ms.readByid(selectedGroupe.getId_groupe());
        displayChatHistory(chatHistory);
    }

    private void displayChatHistory(List<Message> chatHistory) {
        for (Message message : chatHistory) {
            Label messageLabel = new Label(us.rechercheUtilisateur(message.getUid()).getNom() + ": " + message.getText());
            messageLabel.setLineSpacing(10);
            HBox hbox = new HBox(messageLabel);
            UtilisateurService us = new UtilisateurService();
            typing.setText(us.rechercheUtilisateur(Session.getUserId()).getNom().toUpperCase(Locale.ROOT) + " is typing now");
            typing.visibleProperty().bind(messageTextField.textProperty().isNotEmpty());
            if (message.getUid() == Session.getUserId()) {
                messageLabel.setStyle("-fx-background-color: #4B87F6; -fx-background-radius: 20; -fx-font-size: 20; -fx-text-fill: white ");
                hbox.setAlignment(Pos.CENTER_RIGHT);
            } else {
                messageLabel.setStyle("-fx-background-color: #BDBCBD; -fx-background-radius: 20; -fx-font-size: 20; -fx-text-fill: black ");
                hbox.setAlignment(Pos.BASELINE_LEFT);
            }

            vbox.setSpacing(20);
            vbox.getChildren().add(hbox);
            System.out.println(message.getUid());
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

    /*@FXML
    public void handleSendButtonAction(javafx.event.ActionEvent actionEvent) {
        String messageContent = messageTextField.getText().trim();
        if (!messageContent.isEmpty()) {
            // Save the message to the database
            Message newMessage = new Message(selectedGroup.getId_groupe(), String.valueOf(LocalDate.now()), messageContent,Session.getUserId(),0);
            ms.ajouter(newMessage);
            // Update the chatTextArea with the new message
            Label newM = new Label(messageContent);
            newM.setStyle("-fx-background-color: #4B87F6; -fx-background-radius: 40; -fx-font-size: 20; -fx-text-fill: white; ");
            //chatTextArea.appendText(String.valueOf(newM));
            vbox.getChildren().add(newM);
            // Clear the messageTextField
            messageTextField.clear();
        }
    }*/

    public void handleSendButtonActionI(MouseEvent mouseEvent) throws IOException {
        String messageContent = messageTextField.getText().trim();
        if (!messageContent.isEmpty()) {
            String cleanValue = TextFiltringApi(messageContent);
            Message newMessage = new Message(selectedGroup.getId_groupe(), String.valueOf(LocalDate.now()), cleanValue, Session.getUserId(), 0);
            ms.ajouter(newMessage);
            Label newM = new Label(us.rechercheUtilisateur(newMessage.getUid()).getNom() + " : " + cleanValue + "\n");
            Label bad = new Label("Ken fi darkom matrabitch a7na nrabywek ye XXXXX");
            bad.setStyle("-fx-background-color: white; -fx-background-radius: 40; -fx-font-size: 20; -fx-text-fill: red; ");
            newM.wrapTextProperty();
            newM.setStyle("-fx-background-color: #4B87F6; -fx-background-radius: 40; -fx-font-size: 20; -fx-text-fill: white; ");

            if(TextFiltringApi(messageContent)==null){
                // Update the chatTextArea with the new message
                HBox hbox = new HBox(newM);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                vbox.getChildren().add(hbox);
            }else{
                HBox hbox = new HBox(newM,bad);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                vbox.getChildren().addAll(hbox);
            }

            // Save the message to the database


            // Clear the messageTextField
            messageTextField.clear();
        }
    }

    public void chooseFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Upload");
        Stage primaryStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        Image image = new Image(selectedFile.getAbsolutePath());
        ImageView imageV = new ImageView(image);
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            vbox.getChildren().add(imageV);
        } else {
            System.out.println("No file selected.");
        }
    }

    public String TextFiltringApi(String t) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, String.format("{ \"text\": \"%s\", \"maskCharacter\": \"x\", \"language\": \"en\" }", t));
        Request request = new Request.Builder()
                .url("https://profanity-cleaner-bad-word-filter.p.rapidapi.com/profanity")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", "aef1032e49msh9d46f007189dde9p15f3f9jsn879fab779700")
                .addHeader("X-RapidAPI-Host", "profanity-cleaner-bad-word-filter.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseBody);

        // Access the "clean" field in the JSON response
        String cleanValue = root.path("clean").asText();
        System.out.println(cleanValue);

        return cleanValue;
    }

    public void ChatBotRespect(){

    }
}
