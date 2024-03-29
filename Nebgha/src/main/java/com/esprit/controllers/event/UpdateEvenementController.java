package com.esprit.controllers.event;

import com.esprit.models.event.Evenement;
import com.esprit.models.event.Localisation;
import com.esprit.services.event.EvenementService;
import com.esprit.services.event.LocalisationService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateEvenementController implements Initializable {

    @FXML
    private DatePicker Datee;

    @FXML
    private TextArea Description;

    @FXML
    private TextField ImageF;

    @FXML
    private ComboBox<String> Lieu;

    @FXML
    private TextField Nom;

    @FXML
    private Label champ_date;

    @FXML
    private Label champ_desc;

    @FXML
    private Label champ_lieu;

    @FXML
    private Label champ_nom;

    @FXML
    private ImageView imageV;
    EvenementService es = new EvenementService();
    LocalisationService ls = new LocalisationService();
    private String nom_loc;
    Evenement event=new Evenement();
    @FXML
    void Ajouter(ActionEvent event) {
        if (Datee.getValue()== null )
            champ_date.setText("*Champ obligatoire");
        if (Nom.getText().isEmpty() )
            champ_nom.setText("*Champ obligatoire");
        if (Description.getText().isEmpty() )
           champ_desc.setText("*Champ obligatoire");
        if ( Lieu.getSelectionModel().isEmpty())
            champ_lieu.setText("*Champ obligatoire");
        if(!Lieu.getSelectionModel().isEmpty()&&!Description.getText().isEmpty()&&!Nom.getText().isEmpty()&&Datee.getValue()!=null)
        {
            champ_nom.setText("");
            champ_lieu.setText("");
            champ_desc.setText("");
            champ_date.setText("");
            try {


                Evenement e = new Evenement();
                e.setNom(Nom.getText());
                e.setId(this.event.getId());
                LocalDate localDate = Datee.getValue();
                Date sqlDate = java.sql.Date.valueOf(localDate);
                e.setDate(sqlDate);
                e.setDescription(Description.getText());
                e.setImage(ImageF.getText());
                e.setLieuId(ls.findByName(nom_loc));

                es.modifier(e);
                System.out.println("Event ajouté avec succées !");
            } catch (SQLException ex) {
                System.out.println("error" + ex.getMessage());




        }
        }

}


    public Evenement setEvenement(Evenement e) {
        Nom.setText(e.getNom());
        Description.setText(e.getDescription());
        ImageF.setText(e.getImage());
        this.event.setId(e.getId());

        // Convert java.sql.Date to LocalDate
        Date date = e.getDate();
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        Datee.setValue(localDate);

        Lieu.setValue(e.getLieuId().getVille());

        return this.event;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        champ_nom.setText("");
        champ_lieu.setText("");
        champ_desc.setText("");
        champ_date.setText("");
        ImageF.setEditable(false);
        try {
            ListLieu();
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    nom_loc = Lieu.getValue();
                }
            };
            // Set on action
            Lieu.setOnAction(event);

        } catch (SQLException ex) {
            System.out.println(ex);
        }


    }

    @FXML
    void importer(ActionEvent event) throws FileNotFoundException {
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png", "*.jpeg"));
        // for single file
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            ImageF.setText(selectedFile.getName());
            InputStream stream = new FileInputStream(selectedFile.getAbsolutePath());
            Image imagee = new Image(stream);
            imageV.setImage(imagee);

        } else {
            System.out.println("Not valid file");
        }


    }
    public void ListLieu() throws SQLException {
        LocalisationService ls = new LocalisationService();
        ArrayList<Localisation> liste_loc = (ArrayList<Localisation>) ls.recupererLocalisation();
        for (int i = 0; i < liste_loc.size(); i++) {
            Lieu.getItems().add(liste_loc.get(i).getVille());

        }
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/ListEvenement.fxml"));
            root = loader.load();


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene =  new Scene(root, 800, 550);
            stage.setScene(scene);
            stage.setTitle("Ajouter Evenement");
            stage.show();

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
}
