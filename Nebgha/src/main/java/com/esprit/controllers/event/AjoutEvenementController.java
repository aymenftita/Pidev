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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AjoutEvenementController implements Initializable {
    private EvenementService evenementService;


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

    public static String uploadImage(File imageFile, String destinationDirectory) {
        try {
            // Generate a unique file name for the image
            String fileName = imageFile.getName();
            String destFilePath = destinationDirectory + File.separator + fileName;

            // Copy the image file to the destination directory
            Files.copy(imageFile.toPath(), new File(destFilePath).toPath(), StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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
                e.setDate(Date.valueOf(Datee.getValue()));
                e.setDescription(Description.getText());
                String imagePath = Paths.get("src", "main", "resources", "Planning", "Images", ImageF.getText()).toUri().toString();
                e.setImage(imagePath);
                e.setLieuId(ls.findByName(nom_loc));
                es.ajouter(e);
                System.out.println("Event ajouté avec succées !");


            } catch (SQLException ex) {
                System.out.println("error" + ex.getMessage());




        }
        }

}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //NotificationService notificationService = new NotificationService();
       // evenementService = new EvenementService(notificationService);
        champ_nom.setText("");
        champ_lieu.setText("");
        champ_desc.setText("");
        champ_date.setText("");
        ImageF.setEditable(false);
        //if (notificationService != null) {
          //  evenementService = new EvenementService(notificationService);
        //} else {
            // Gérez le cas où le service de notification est nul
          //  System.err.println("Le service de notification est nul. Assurez-vous qu'il est correctement initialisé.");
        //}
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

        // Show the file chooser dialog
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            // Upload the selected image file
            String imageFileName = uploadImage(selectedFile, "src/main/resources/Planning/Images");

            // Store the image file name (or path) in the ImageF TextField
            ImageF.setText(imageFileName);

            // Display the selected image
            Image image = new Image(selectedFile.toURI().toString());
            imageV.setImage(image);
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
