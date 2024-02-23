package com.esprit.tests;

//import com.esprit.services.EntityService;


import com.esprit.services.ModuleService;

public class MainProg {

    public static void main(String[] args) {
          ModuleService ms = new ModuleService();

//        ps.supprimer(new Personne(3, "Ahmed", "Med"));
//        ps.modifier(new Personne(1, "Emma", "Zouaoui"));
        System.out.println(ms.afficher());



    }
}
