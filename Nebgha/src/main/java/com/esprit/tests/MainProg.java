package com.esprit.tests;

//import com.esprit.services.EntityService;


import com.esprit.models.module;
import com.esprit.services.ModuleService;

public class MainProg {

    public static void main(String[] args) {
          ModuleService ms = new ModuleService();
          ms.ajouter(new module(1,"Module 1","domaine A","1"));
//        ps.supprimer(new Personne(3, "Ahmed", "Med"));
//        ps.modifier(new Personne(1, "Emma", "Zouaoui"));
//        System.out.println(ps.afficher());



    }
}
