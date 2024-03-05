package com.esprit.tests;

import com.esprit.services.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainProgGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Session.login(2);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interfaces/AjouterReclamation.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,1000,700);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

