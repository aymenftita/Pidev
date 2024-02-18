package com.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;

public class ModifierMessageController {

    public ComboBox<String> cb;
    public Label lb;

    public void initialize() {
        cb.setItems(FXCollections.observableArrayList("envoyé","En cours de traitement", "Traité"));
    }

    public void setLb(String text){
        lb.setText(text);
    }


    public void go(ActionEvent actionEvent) {
        lb.setText(cb.getValue());
    }
}
