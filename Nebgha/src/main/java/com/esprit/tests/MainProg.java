package com.esprit.tests;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import java.sql.Date;
import com.esprit.services.LocalisationService;
import com.esprit.models.Localisation;
public class MainProg {

    public static void main(String[] args) {


            // Adresse pour laquelle vous souhaitez obtenir les coordonnées
            String address = "paris,France";

            // Instanciation de LocalisationService
            LocalisationService localisationService = new LocalisationService();

            // Appel de la méthode pour obtenir les coordonnées
            Localisation coordinates = localisationService.getCoordinatesFromAddress(address);
        double longitude = coordinates.getLongitude();
        System.out.println(longitude);
        //coordinates.setVille("NomDeLaVille");
        //coordinates.setPays("NomDeLapays");
        localisationService.enregistrer(coordinates );
            // Vérification que les coordonnées ne sont pas nulles
            if (coordinates != null) {
                System.out.println("Coordonnées obtenues : " + coordinates.getLatitude() + ", " + coordinates.getLongitude());
            } else {
                System.out.println("Les coordonnées n'ont pas pu être obtenues.");
            }

        }
       // EvenementService ps = new EvenementService();


      //  ps.ajouter(new Evenement(2, "amine" ,Date.valueOf("2001-03-28"),1 ,2,1,"oui"));
   // ps.supprimer(new Evenement(9 ,2,  "GHADA" ,Date.valueOf("2001-03-28") ,1,2,2,1,1 , "non"));
      //ps.modifier(new Evenement( 6 ,1,  "GHADA" ,Date.valueOf("2001-03-28") ,2,1,2,2,1 , "non") );
//        System.out.println(ps.afficher());
       // PersonneService2 ps2 = new PersonneService2();
        //ps2.ajouter(new Personne("Fedi2", "Naoufel"));
    }

