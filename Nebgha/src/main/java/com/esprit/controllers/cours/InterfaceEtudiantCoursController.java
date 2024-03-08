package com.esprit.controllers.cours;

import com.esprit.models.cours.Cours;
import com.esprit.models.cours.UserCours;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.cours.CoursService;
import com.esprit.services.Session;
import com.esprit.services.cours.UserCoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class InterfaceEtudiantCoursController {

        @FXML
        private AnchorPane anchorPane;

        @FXML
        private Text text;

        @FXML
        private ImageView imageView1;

        @FXML
        private ImageView imageView2;

        @FXML
        private ImageView logoImageView;

        @FXML
        private TableColumn<?, ?> descriptionColumn;

        @FXML
        private TableColumn<?, ?> titreColumn;

        @FXML
        private TableColumn<?, ?> contenuColumn;

        @FXML
        private ImageView ebookIconImageView;

    @FXML
    private TableView<Cours> coursTable;

    @FXML
    private TextField tfSearchCours;
    @FXML
    private TableView<Cours>tvAffichageCours;
    @FXML
    private ComboBox<String> cbSortCours;
    @FXML
    private ComboBox<String> cbSort;

    CoursService cs=new CoursService();

    private final ObservableList<Cours> CoursList = FXCollections.observableArrayList(cs.afficher());


    @FXML
    public void initialize(){
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        contenuColumn.setCellValueFactory(new PropertyValueFactory<>("Contenu"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));


        coursTable.setItems(CoursList);

        List<String> sortCours = new ArrayList<>();
        sortCours.add("Title");
        sortCours.add("Content");

        cbSort.getItems().addAll(sortCours);
    }


    @FXML
    void Participer(ActionEvent event) throws IOException{
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            System.out.println("Partciper aux cours : " + selectedCours);
        }
        

    }


    @FXML
    public void Back(ActionEvent event) throws IOException {
        changeScene(event, "/cours/InterfaceEtudiant.fxml","Interface Etudiant");
    }

    @FXML
    void updateInterface(ActionEvent event) throws IOException {
        System.out.println("Mise à jour de l'interface");
        changeScene(event, "/cours/InterfaceEtudiantModule.fxml","Liste des modules");
    }

    private void changeScene(ActionEvent event,String fxmlPath,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    @FXML
    void handleSearch() {
        CoursService CS = new CoursService();
        ObservableList<Cours> dataCours = FXCollections.observableArrayList(CS.afficher());
        FilteredList<Cours> filteredData = new FilteredList<>(dataCours, b -> true);
        tfSearchCours.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(cours -> { if (newValue == null || newValue.isEmpty()) { return true; } String lowerCaseFilter = newValue.toLowerCase();
            return cours.getTitre().toLowerCase().contains(lowerCaseFilter);
        }));

    }

    @FXML
    void handleSortCours(ActionEvent event) {
        FilteredList<Cours> filteredData = (FilteredList<Cours>) tvAffichageCours.getItems();

        SortedList<Cours> sortedData = new SortedList<>(filteredData);

        Comparator<Cours> TitreComparator = (m1, m2) -> m2.getTitre().compareTo(m1.getTitre());
        Comparator<Cours> ContenuComparator = (m1, m2) -> m2.getContenu().compareTo(m1.getContenu());


        sortedData.comparatorProperty().bind(cbSortCours.getSelectionModel().selectedItemProperty().asString().map((Function<? super String, ? extends Comparator<? super Cours>>) s -> {
            if (s.equals("Content")) {
                return ContenuComparator;
            }
            return TitreComparator;
        }));

        tvAffichageCours.setItems(sortedData);
    }
    @FXML
    void handleParticiper(ActionEvent event) throws InterruptedException {
        UserCoursService ucs = new UserCoursService();
        CoursService cs = new CoursService();
        Cours SlectedCours = coursTable.getSelectionModel().getSelectedItem();
        UserCours uc = new UserCours(Session.getCurrentUser().getId(), SlectedCours.getId_cours());
        ucs.ajouter(uc);
        /*Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            Class<? extends InterfaceEtudiantCoursController> currentUser = getClass();
            if (currentUser != null) {
                CoursService cs = new CoursService();
                boolean isParticipated = cs.participerCours(currentUser, selectedCours);
                if (isParticipated) {
                    // show a success message*/
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Participation réussie");
                    alert.setHeaderText("Vous avez réussi à participer au cours " + SlectedCours.getTitre());
                    alert.showAndWait();
               /* } else {
                    // show an error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de participation");
                    alert.setHeaderText("Une erreur est survenue lors de la participation au cours " + selectedCours.getTitre());
                    alert.showAndWait();
                }
            } else {
                // show a warning message
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Utilisateur non connecté");
                alert.setHeaderText("Vous devez être connecté pour participer à un cours");
                alert.showAndWait();
            }
        } else {
            // show a warning message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun cours sélectionné");
            alert.setHeaderText("Veuillez sélectionner un cours à participer");
            alert.showAndWait();
        }*/
    }

    @FXML
    void consulterHistorique(ActionEvent event) throws IOException {
        Cours selectedCours = coursTable.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            List<Utilisateur> participants = selectedCours.getParticipants();
            // display the list of participants and their progress in a new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/CourseHistory.fxml"));
            Parent root = loader.load();
            CourseHistoryController controller = loader.getController();
            /*controller.setParticipants(participants);*/
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Historique de cours");
            stage.show();
        }
    }

    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceEtudiant.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }

}
