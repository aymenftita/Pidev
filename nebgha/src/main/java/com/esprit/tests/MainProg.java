package com.esprit.tests;

import com.esprit.services.GroupeService;
import com.esprit.services.ReclamationService;
import com.esprit.services.UtilisateurService;
import java.sql.SQLException;

public class MainProg {

    public static void main(String[] args) throws SQLException {
        GroupeService gs = new GroupeService();
        //ReclamationService rs = new ReclamationService();
        //UtilisateurService us =new UtilisateurService();
        //test de reclamation valid
        //Reclamation r = new Reclamation(user,"01/01/2024","testSujet1","testDescription1","testStatut1",1,"testResponsable1");

        System.out.println("******************************************************");
        System.out.println(gs.afficher());



        //Groupe g3 = new Groupe(1,1,"test","descriptiontest");

        //Groupe g2 = new Groupe(0,1,"test Title","test Description");

        //MessageService ms = new MessageService();

        //Message m = new Message(1,1,"dzdzd","hfhff");



    }
}
