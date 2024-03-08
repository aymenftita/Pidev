package com.esprit.controllers.cours;

import com.esprit.models.cours.Cours;
import com.esprit.models.cours.UserCours;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class CourseHistoryController {


    @FXML
    private TableView<Cours>coursTable;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> titreColumn;

    @FXML
    private TableColumn<?, ?> contenuColumn;

    @FXML
    private TextField tfSearchCours;
    @FXML
    private TableView<Cours>tvAffichageCours;
    @FXML
    private ComboBox<String> cbSortCours;
    @FXML
    private ComboBox<String> cbSort;



    CoursService cs=new CoursService();
    UserCoursService ucs = new UserCoursService();
    List<UserCours> uc = new ArrayList<>();

    List<Cours> cl = new ArrayList<>();
    private final ObservableList<Cours> CoursList = FXCollections.observableArrayList(cs.afficher());

    @FXML
    public void initialize(){
        uc = ucs.getCoursByUseId(Session.getCurrentUser().getId());
        for (UserCours c : uc  ){
            cl.add(cs.getCours(c.getCid()));
        }



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
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceEtudiant.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
}
