package com.esprit.controllers.groupeControllers;
import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Groupe;
import com.esprit.models.Utilisateur;
import com.esprit.services.GroupeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;


public class AfficherGroupeController {


    public TextField tf;
    @FXML
    private TableView<Groupe> tableView;

    @FXML
    private TableColumn<Groupe, Integer> idColumn;
    @FXML
    private TableColumn<Groupe, Utilisateur> idColumng;

    @FXML
    private TableColumn<Groupe, String> nomColumn;
    @FXML
    private TableColumn<Groupe, String> dateColumn;

    GroupeService gs = new GroupeService();
    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    private ObservableList<Groupe> groupe = FXCollections.observableArrayList(gs.afficher());


    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_groupe"));
        idColumng.setCellValueFactory(new PropertyValueFactory<>("uid"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        //chargerDonnees();

        tableView.setItems(groupe);
        System.out.println(groupe);
    }


    public void supprimerSelection(ActionEvent actionEvent) throws Exception {
        try {
            Groupe selectedGroupe = tableView.getSelectionModel().getSelectedItem();
            int id = selectedGroupe.getId_groupe();

            GroupeService gs =new GroupeService();

            gs.supprimer(id);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Groupe Supprimé");
            alert.show();

            tableView.refresh();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Aucune selection detecté");
            alert.setContentText(e.getMessage());
            alert.setContentText("Selectione une ligne");
            alert.show();
        }

    }

    public Groupe selectedGroupe(){
      return  tableView.getSelectionModel().getSelectedItem();
    }


    public void SwitchToAfficherMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherMessage",tf);
    }

    public void SwitchToAfficherReclamation(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherReclamation",tf);
    }

    public void SwitchToAjouterGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AjouterGroupe");
    }


    public void SwitchToModifierGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"ModifierGroupe");
    }




    public Groupe getSelectedGroupe() {
        return tableView.getSelectionModel().getSelectedItem();
    }





    /*private void chargerDonnees() {
        // Connectez-vous à la base de données et exécutez une requête SQL pour récupérer les données
        // Assurez-vous d'adapter ces informations à votre propre configuration de base de données
        String url = "jdbc:mysql://localhost:3306/nebgha";
        String utilisateur = "root";
        String motDePasse = "";

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            String sql = "SELECT * FROM groupe";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {

                while (rs.next()) {

                    Integer id_groupe = rs.getInt("id_groupe");
                    Integer creator_id = rs.getInt("creator_id");
                    String titre = rs.getString("titre");
                    String descripton = rs.getString("description");

                    groupe.add(new Groupe(id_groupe,creator_id,titre,descripton));


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

}