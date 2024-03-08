package com.esprit.controllers.cours;

import com.esprit.models.cours.Cours;
import com.esprit.services.cours.CoursService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class InterfaceTuteurCours {

    @FXML
    private TableView<Cours> coursTable;

    @FXML
    private TableColumn<Cours, String> titreColumn;

    @FXML
    private TableColumn<Cours, String> contenuColumn;

    @FXML
    private TableColumn<Cours, String> descriptionColumn;

    @FXML
    private ImageView updateImageView;

    @FXML
    private Text listeCoursText;
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
    void ajouterCours() {
        // Code pour ajouter un nouveau cours
        Cours nouveauCours = new Cours(1, "Titre du cours", "Contenu du cours", "Description du cours", 1);
        coursTable.getItems().add(nouveauCours);
    }

    @FXML
    void modifierCours() {
        Cours coursSelectionne = coursTable.getSelectionModel().getSelectedItem();
        if (coursSelectionne != null) {
            coursTable.getItems().addAll();
        }
    }

    @FXML
    void supprimerCours() {
        Cours coursSelectionne = coursTable.getSelectionModel().getSelectedItem();
        if (coursSelectionne != null) {
            coursTable.getItems().remove(coursSelectionne);
        }
    }
    @FXML
    public void Back(ActionEvent event) throws IOException {
        changeScene(event, "/cours/InterfaceTuteur.fxml","Supprimer Lecons");
    }

    private void changeScene(ActionEvent event, String fxmlPath,String title) throws IOException {
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
    void openAdd(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AjoutCours.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
    @FXML
    void openEdit(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierCours.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }

}
