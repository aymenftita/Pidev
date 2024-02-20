package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchScenesController {
    public void SwitchScene(ActionEvent event,String fxmlfile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/"+fxmlfile+".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void switchScene(Stage stage, String fxmlfile) {
        try {
            FXMLLoader loader = new FXMLLoader(SwitchScenesController.class.getResource("/"+fxmlfile+".fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


