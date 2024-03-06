package com.esprit.tests;

import java.text.ParseException;

public class MainProg {

    public static void main(String[] args) throws ParseException {
        /*************      SUJET         *************
         Connection connection = DataSource.getInstance().getConnection();

         sujet testSujet1 = new sujet(1, "TestModif1", "Test modif Desc", "Tests modifRegles");

         sujet testSujet2 = new sujet(2, "Test2", "Test Desc", "Tests Regles");
         sujet testSujet3 = new sujet(3, "Test3", "Test Desc", "Tests Regles");

         sujetService e = new sujetService();
         e.ajouter(new sujet(1, "Test1", "Test Description", "Tests Regles"));
         e.modifier(testSujet1);
         e.supprimer(testSujet2);
         List listAffichage = e.afficher();
         for(int i=0;i<listAffichage.size();i++){
         System.out.println(listAffichage.get(i));
         }
         */

        /*********************       QUESTION            ********************/
        //Question testQuestion2 = new Question(2, "Test titre",2, Date.valueOf("2022-12-03"), new Sujet(1, "TestModif1", "Test modif Desc", "Tests modifRegles"), "test contenu");
        //questionService q = new questionService();
        //Question testQuestion = new Question(10, "Test modifTitre",1, Date.valueOf("2022-12-03"), 1, "test modifContenu");

        //q.ajouter(testQuestion);
        //q.modifier(testQuestion2);
        //q.ajouter(new question(3, "test titre3", 1, Date.valueOf("2024-05-25"), 1, "test contenu3"));
        //q.supprimer(testQuestion1);
/*
        List listAffichage = q.afficher();
        for(int i=0;i<listAffichage.size();i++){
            System.out.println(listAffichage.get(i));
        }
*/
         /*/

        /*************************        REPONSE      *************************
        Reponse testReponse1 = new Reponse(2, 1,
                new Question(13, "test", 2,Date.valueOf("2024-02-13"),
                        new Sujet(1, "TestModif1", "Test modif Desc", "Tests modifRegles"),
                        "dadza"), "contenu", Date.valueOf("2024-02-13"),
                new Sujet(1, "TestModif1", "Test modif Desc", "Tests modifRegles"));
        reponseService r = new reponseService();
        //reponse testReponse2 = new reponse(1, 1,3, "test modifContenu", Date.valueOf("12/07/2021"), 1);

        //r.ajouter(testReponse1);
        r.modifier(testReponse1);
        //r.ajouter(new reponse(3, 1, 2, "contenu", Date.valueOf("12/07/2021"), 1));
        //r.supprimer(testReponse1);
*
        List listAffichage = r.afficher();
        for(int i=0;i<listAffichage.size();i++){
            System.out.println(listAffichage.get(i));
        }

         */
    }
}
