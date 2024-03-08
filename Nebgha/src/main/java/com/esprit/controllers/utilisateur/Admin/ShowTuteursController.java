package com.esprit.controllers.utilisateur.Admin;

import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Tuteur;
import com.esprit.services.Session;
import com.esprit.services.utilisateur.ServiceTuteur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ShowTuteursController implements Initializable {

    @FXML
    private TableColumn<Tuteur, String> nom;

    @FXML
    private TableColumn<Tuteur, String> prenom;

    @FXML
    private TableColumn<Tuteur, String> email;
    @FXML
    private TableColumn<Tuteur, String> domaine;
    @FXML
    private TableColumn<Tuteur, String> experience;
    @FXML
    private TableView<Tuteur> tuteurTableView;
@FXML
    private Label nameLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(Session.getCurrentUser().getNom());
        ServiceTuteur serviceTuteur = new ServiceTuteur();
        List<Tuteur> tuteurs = serviceTuteur.afficher();

        ObservableList<Tuteur> tuteurObservableList = FXCollections.observableArrayList(tuteurs);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        domaine.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        experience.setCellValueFactory(new PropertyValueFactory<>("experience"));

        tuteurTableView.setItems(tuteurObservableList);

        tuteurTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Tuteur> row = new javafx.scene.control.TableRow<>();
            return row;
        });

        TableColumn<Tuteur, Void> deleteTuteurColumn = new TableColumn<>("Delete");
        deleteTuteurColumn.setPrefWidth(75);
        deleteTuteurColumn.setCellFactory(param -> new TableCell<Tuteur, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Tuteur tuteur = getTableView().getItems().get(getIndex());
                    serviceTuteur.supprimer(tuteur);
                    getTableView().getItems().remove(tuteur);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        tuteurTableView.getColumns().add(deleteTuteurColumn);
    }

    @FXML
    void AjoutTuteur(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AjouterTuteur.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ShowUsers.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
    @FXML
    void ApercuForum(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/interfacesSujet/InterfaceForumUser.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();

    }

    @FXML
    public void ApercuPlanning(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/ListEvenement.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }

    @FXML
    public void ApercuCours(MouseEvent event) throws IOException {
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceTuteur.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceEtudiant.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceAdmin.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
    }
    @FXML
    public void ApercuGroups(MouseEvent event) throws IOException {
        if (Session.getCurrentRole().equals(Role.Administrateur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationEtGroupesChatRessources/Interfaces/AfficherGroupe.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationEtGroupesChatRessources/Interfaces/testFlow.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
    }

    @FXML
    public void ApercuQuiz(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/TuteurInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/EtudiantInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/AdminInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }

    }

    public void apercuProfile(MouseEvent mouseEvent) throws IOException {
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ModifierTuteur.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ModifierEtudiant.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ModifierAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
