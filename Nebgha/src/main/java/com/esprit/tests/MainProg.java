package com.esprit.tests;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import java.sql.Date;

public class MainProg {

    public static void main(String[] args) {
        EvenementService ps = new EvenementService();


      //  ps.ajouter(new Evenement(2, "amine" ,Date.valueOf("2001-03-28"),1 ,2,1,"oui"));
    ps.supprimer(new Evenement(9 ,2,  "GHADA" ,Date.valueOf("2001-03-28") ,2,1,1 , "non"));
      ps.modifier(new Evenement( 6 ,1,  "GHADA" ,Date.valueOf("2001-03-28") ,2,1,1 , "non") );
//        System.out.println(ps.afficher());
       // PersonneService2 ps2 = new PersonneService2();
        //ps2.ajouter(new Personne("Fedi2", "Naoufel"));
    }
}
