package com.esprit.tests;

import com.esprit.models.*;
import com.esprit.services.*;

import java.sql.Date;
import java.util.*;

public class MainProg {

    public static void main(String[] args) {
        QuizService qs = new QuizService();
        //qs.ajouter(new Quiz(1,"Quiz test type", "Description du quiz 2", 30, 10, Difficulte.avancé));
        // qs.supprimer(new Quiz(2,1,"Quiz 1 modif", "Description du quiz 1", Calendar.getInstance().getTime(), 30, 10, Difficulte.avancé));
        //qs.modifier(new Quiz(1,1,"Quiz 1 modif", "Description du quiz 1", Calendar.getInstance().getTime(), 30, 10, Difficulte.débutant));
       // System.out.println(qs.afficher());

        QuestionsService qus = new QuestionsService();
       // QuizService quizService = new QuizService();
        //Quiz quiz = quizService.getQuiz(1);
        //qus.ajouter(new Questions(quiz, "Texte de la question 2", "Type de question 2", 10, 1, "Catégorie 1"));
        //qus.ajouter(new Questions(2, "Texte de la question 2", "Type de question 2", 15, 2, "Catégorie 2"));
       // qus.supprimer(new Questions(2,2, "Texte de la question 2", "Type de question 2", 15, 2, "Catégorie 2"));
        //qus.modifier(new Questions(1,1, "Texte de la question 1 modif", "Type de question 2", 15, 2, "Catégorie 2"));
       //System.out.println(qus.afficherQuestionsQuiz(3));

        RecompensesService recompensesService = new RecompensesService();
 //recompensesService.ajouter(new Recompenses("Nom recompense", "Description recompense", 100));
 //recompensesService.supprimer(new Recompenses(2, "Nom recompense", "Description recompense", 100));
 //recompensesService.modifier(new Recompenses(2, "Nom recompense modifié", "Description recompense", 100));
//System.out.println(recompensesService.afficher());

        ReponsesUtilisateurService reponsesUtilisateurService = new ReponsesUtilisateurService();
      //  reponsesUtilisateurService.ajouter(new ReponsesUtilisateur(1, 1, 1, Date.valueOf("2024-02-21"), 20, true));
 //reponsesUtilisateurService.supprimer(new ReponsesUtilisateur(5, 1, 1, 1, Date.valueOf("2024-02-21"), 20, true));
 //reponsesUtilisateurService.modifier(new ReponsesUtilisateur(5,1, 1, 1, Date.valueOf("2024-02-21"), 30, true));
 //System.out.println(reponsesUtilisateurService.afficher());

        ReponsesService reponsesService = new ReponsesService();
 //reponsesService.ajouter(new Reponses(1, "Texte de la réponse 2", true, 1, "Explication réponse 1"));
 //reponsesService.supprimer(new Reponses(2,1, "Texte de la réponse 1 modifié", true, 1, "Explication réponse 1"));
 //reponsesService.modifier(new Reponses(2,1, "Texte de la réponse 2 modifié", true, 1, "Explication réponse 1"));
 //System.out.println(reponsesService.afficher());

        RecompensesUtilisateurService recompensesUtilisateurService = new RecompensesUtilisateurService();
 //recompensesUtilisateurService.ajouter(new RecompensesUtilisateur(1, 1, Date.valueOf("2024-02-21"), true, Date.valueOf("2024-02-25")));
// recompensesUtilisateurService.supprimer(new RecompensesUtilisateur(3,1, 1, Date.valueOf("2024-02-25"), false, Date.valueOf("2024-02-25")));
        //recompensesUtilisateurService.modifier(new RecompensesUtilisateur(3,1, 1, Date.valueOf("2024-02-21"), false, Date.valueOf("2024-02-25")));
 //System.out.println(recompensesUtilisateurService.afficher());

    }
}
