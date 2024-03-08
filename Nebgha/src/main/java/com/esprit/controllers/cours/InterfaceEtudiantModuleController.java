package com.esprit.controllers.cours;

import com.esprit.models.cours.module;
import com.esprit.services.cours.ModuleService;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class InterfaceEtudiantModuleController {


    @FXML
    private TableView<module> tableModule;

        @FXML
        private TableColumn<module, String> titreColumn;

        @FXML
        private TableColumn<module, String> niveauColumn;

        @FXML
        private TableColumn<module, String> domaineColumn;

        @FXML
        private Button accederButton;

        @FXML
        private Button previousIconButton;

        @FXML
        private Button updateIconButton;

        @FXML
        private TextField tfSearchModule;

        @FXML
        private TableView<module>tvAffichageModule;

        @FXML
        private ComboBox<String> cbSortModules;

        @FXML
        private ComboBox<String> cbSort;

    ModuleService ms=new ModuleService();

    private final ObservableList<module> ModulesList = FXCollections.observableArrayList(ms.afficher());

    @FXML
    public void initialize(){
        titreColumn.setCellValueFactory(new PropertyValueFactory<module, String>("Titre"));
        niveauColumn.setCellValueFactory(new PropertyValueFactory<module, String>("niveau"));
        domaineColumn.setCellValueFactory(new PropertyValueFactory<module, String>("domaine"));

        tableModule.setItems(ModulesList);

        List<String> sortModules = new ArrayList<>();
        sortModules.add("Title");
        sortModules.add("Level");

        cbSort.getItems().addAll(sortModules);
    }


    @FXML
        void acceder(ActionEvent event) throws IOException {
            module selectedModule = tableModule.getSelectionModel().getSelectedItem();
            if (selectedModule != null) {
                System.out.println("Accéder au module : " + selectedModule.toString());
            }
        changeScene(event, "/InterfaceEtudiantCours.fxml","Liste des Cours");
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
        ModuleService MS = new ModuleService();
        ObservableList<module> dataModule = FXCollections.observableArrayList(MS.afficher());
        FilteredList<module> filteredData = new FilteredList<>(dataModule, b -> true);
        tfSearchModule.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(module -> { if (newValue == null || newValue.isEmpty()) { return true; } String lowerCaseFilter = newValue.toLowerCase();
            return module.getTitre().toLowerCase().contains(lowerCaseFilter);
        }));

    }

    @FXML
    void handleSortModules(ActionEvent event) {
        FilteredList<module> filteredData = (FilteredList<module>) tvAffichageModule.getItems();

        SortedList<module> sortedData = new SortedList<>(filteredData);

        Comparator<module> TitreComparator = (m1, m2) -> m2.getTitre().compareTo(m1.getTitre());
        Comparator<module> NiveauComparator = (m1, m2) -> m2.getNiveau().compareTo(m1.getNiveau());


        sortedData.comparatorProperty().bind(cbSortModules.getSelectionModel().selectedItemProperty().asString().map((Function<? super String, ? extends Comparator<? super module>>) s -> {
            if (s.equals("Level")) {
                return NiveauComparator;
            }
            return TitreComparator;
        }));

        tvAffichageModule.setItems(sortedData);
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


