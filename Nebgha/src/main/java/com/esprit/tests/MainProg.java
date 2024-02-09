package com.esprit.tests;

import com.esprit.models.entity;
//import com.esprit.services.EntityService;
import com.esprit.services.EntityService2;

public class MainProg {

    public static void main(String[] args) {
        //EntityService e = new EntityService();
//        ps.ajouter(new Personne("Ahmed", "Med"));
//        ps.supprimer(new Personne(3, "Ahmed", "Med"));
//        ps.modifier(new Personne(1, "Emma", "Zouaoui"));
//        System.out.println(ps.afficher());
        EntityService2 e2 = new EntityService2();
        e2.ajouter(new entity("Fedi2", "Naoufel"));
    }
}
