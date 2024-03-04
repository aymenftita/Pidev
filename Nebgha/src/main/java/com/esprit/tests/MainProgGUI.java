package com.esprit.tests;

import com.esprit.controllers.CarteGeoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainProgGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListLocalisation.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 800, 500);
            primaryStage.setScene(scene);

            primaryStage.setTitle("Menu ghada");
            primaryStage.show();

        } catch (IOException ex) {
           System.out.println("error" + ex.getMessage());
        }
    }
}