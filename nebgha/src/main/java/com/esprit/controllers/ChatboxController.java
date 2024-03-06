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
            messageLabel.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Are you sure?");
                alert.setContentText("Do you want to report this message?");

                // Customize the button types (OK, Cancel)
                alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

                // Show and wait for the user's response
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        message.setSignal(1);
                        ms.modifier(message);
                        // Add your logic for OK button action here
                    }
                });
            });
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



    public void handleSendButtonActionI(MouseEvent mouseEvent) throws IOException {
        String messageContent = messageTextField.getText().trim();
        if (!messageContent.isEmpty()) {

            if(TextFiltringApi(messageContent).equals(messageContent)){
                String cleanValue = TextFiltringApi(messageContent);
                Message newMessage = new Message(selectedGroup.getId_groupe(), String.valueOf(LocalDate.now()), cleanValue, Session.getUserId(), 0);
                ms.ajouter(newMessage);
                Label newM = new Label(us.rechercheUtilisateur(newMessage.getUid()).getNom() + " : " + cleanValue + "\n");
                newM.wrapTextProperty();
                newM.setStyle("-fx-background-color: #4B87F6; -fx-background-radius: 40; -fx-font-size: 20; -fx-text-fill: white; ");
                // Update the chatTextArea with the new message
                HBox hbox = new HBox(newM);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                vbox.getChildren().add(hbox);
            }else{
                String cleanValue = TextFiltringApi(messageContent);
                Message newMessage = new Message(selectedGroup.getId_groupe(), String.valueOf(LocalDate.now()), cleanValue, Session.getUserId(), 1);
                ms.ajouter(newMessage);
                Label newM = new Label(us.rechercheUtilisateur(newMessage.getUid()).getNom() + " : " + cleanValue + "\n");
                Label bad = new Label("PlZ respect others!!! , your message is automaticly reported");
                bad.setStyle("-fx-background-color: red; -fx-background-radius: 40; -fx-font-size: 20; -fx-text-fill: white; ");
                newM.wrapTextProperty();
                newM.setStyle("-fx-background-color: #4B87F6; -fx-background-radius: 40; -fx-font-size: 20; -fx-text-fill: white; ");
                HBox hboxBad =new HBox(bad);
                hboxBad.setAlignment(Pos.CENTER_RIGHT);
                HBox hbox = new HBox(newM);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                vbox.getChildren().addAll(hbox,hboxBad);
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

    public void AutoCorrectApi() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"text\": \"Tihs snetence has erors in \",\r\n    \"keyboard\": \"QWERTY\",\r\n    \"languages\": [\r\n        \"en\"\r\n    ]\r\n}");
        Request request = new Request.Builder()
                .url("https://typewise-ai.p.rapidapi.com/correction/whole_sentence")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", "aef1032e49msh9d46f007189dde9p15f3f9jsn879fab779700")
                .addHeader("X-RapidAPI-Host", "typewise-ai.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseBody);

        // Access the "clean" field in the JSON response
        String cleanValue = root.path("corrected_text").asText();
        System.out.println(cleanValue);

    }
}
