package com.esprit.controllers.utilisateur;


import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.utilisateur.ServiceUtilisateur;
import com.esprit.services.Session;
import com.esprit.services.utilisateur.SmsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


import java.util.Optional;

public class ForgotPasswordController {

    private ServiceUtilisateur utilisateurService = new ServiceUtilisateur();
    private SmsService smsService = new SmsService();

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    void sendResetCode(ActionEvent event) {
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();

        Optional<Utilisateur> userOptional = utilisateurService.afficher().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        System.out.println(utilisateurService.afficher());
        System.out.println(email);
        System.out.println(userOptional);
        if (userOptional.isPresent()) {
            Utilisateur user = userOptional.get();
            String resetCode = generateResetCode();
            Session.setResetCode(user.getEmail(), resetCode); // Pass the user email directly
            if (Session.getResetCode(email) != null) {
                smsService.sendResetCodeSMS(phoneNumber, resetCode); // Send SMS with reset code
                showAlert("Reset Code Sent", "A reset code has been sent to your phone.");
                navigateToResetPassword(user);
            }
        } else {
            showAlert("Email not found", "The provided email does not exist.");
        }
    }
    private void navigateToResetPassword(Utilisateur user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ResetPassword.fxml"));
            Parent root = loader.load();
            ResetPasswordController resetPasswordController = loader.getController();
            resetPasswordController.initData(user);            Stage stage = (Stage) emailTextField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private String generateResetCode() {
        // Generate a random code (e.g., a 6-digit number)
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
