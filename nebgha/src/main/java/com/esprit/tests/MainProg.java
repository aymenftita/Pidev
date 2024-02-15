package com.esprit.tests;

import com.esprit.models.Groupe;
import com.esprit.services.GroupeService;
//import com.esprit.services.EntityService;
import com.esprit.services.ReclamationService;

import java.sql.SQLException;

public class MainProg {

    public static void main(String[] args) throws SQLException {
//
        ReclamationService rs = new ReclamationService();

        //test de reclamation valid
        /*Reclamation r = new Reclamation(1,"01/01/2024","testSujet1","testDescription1","testStatut1",1,"testResponsable1");
        rs.ajouter(r);*/

        GroupeService gs = new GroupeService();

        Groupe g3 = new Groupe(1,1,"test","descriptiontest");

        Groupe g2 = new Groupe(0,1,"test Title","test Description");



        gs.afficher();




    }
}
