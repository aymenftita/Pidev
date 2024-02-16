package com.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class MainProgGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = FXMLLoader.load(getClass().getResource("Ajouter.fxml"));
        primaryStage.setTitle("hello");
        primaryStage.setScene(new Scene(root,300,275));
        primaryStage.show();
    }
}
