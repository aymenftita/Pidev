package com.esprit.controllers;
import javafx.scene.control.Alert;
import org.controlsfx.control.Notifications;
public class NotificationUtil {


    public static void showInfoNotification(String message) {
        Notifications.create()
                .title("Information")
                .text(message)
                .showInformation();
    }

    public static void showErrorNotification(String message) {
        Notifications.create()
                .title("Error")
                .text(message)
                .showError();
    }

    
}
