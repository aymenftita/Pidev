package com.esprit.services;

import org.controlsfx.control.Notifications;

public class NotificationService {

    public void showInformationNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

    public void showWarningNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showWarning();
    }

    public void showErrorNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showError();
    }

    public void showEventAddedNotification(String eventName, String eventDescription) {
        String notificationMessage = "Événement ajouté : " + eventName + "\nDescription : " + eventDescription;
        Notifications.create()
                .title("Nouvel Événement")
                .text(notificationMessage)
                .showInformation();
    }
}
