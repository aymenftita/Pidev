package com.esprit.tests;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;


public class MainProg {

    public static void main(String[] args) {
        EvenementService ps = new EvenementService();


        ps.ajouter(new Evenement(1, "Med" ,28:03:2001,1 ,2,1,"oui"));
//        ps.supprimer(new Personne(3, "Ahmed", "Med"));
//        ps.modifier(new Personne(1, "Emma", "Zouaoui"));
//        System.out.println(ps.afficher());
       // PersonneService2 ps2 = new PersonneService2();
        //ps2.ajouter(new Personne("Fedi2", "Naoufel"));
    }
}
