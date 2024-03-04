package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import com.esprit.models.Localisation;
import com.esprit.services.LocalisationService;

import java.util.List;

public class GeoController {

    @FXML
    private WebView geo;

    private final LocalisationService localisationService = new LocalisationService();

    @FXML
    void afficherCoordonneesSurCarte(ActionEvent event) {
        // Récupérer la liste des localisations
        List<Localisation> localisations = localisationService.afficher();

        if (!localisations.isEmpty()) {
            // Afficher la première localisation sur la carte
            Localisation premiereLocalisation = localisations.get(0);
            afficherCoordonnees(premiereLocalisation);
        }
    }

    private void afficherCoordonnees(Localisation localisation) {
        if (localisation != null) {
            double latitude = localisation.getLatitude();
            double longitude = localisation.getLongitude();

            WebEngine webEngine = geo.getEngine();
            webEngine.load("https://www.openstreetmap.org/export/embed.html?bbox=" + (longitude - 0.01) + ","
                    + (latitude - 0.01) + "," + (longitude + 0.01) + "," + (latitude + 0.01) + "&layer=mapnik");
        } else {
            System.out.println("La liste des localisations est vide.");
        }
    }
}
