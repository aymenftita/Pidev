package com.esprit.services;
import com.esprit.models.Difficulte;
import com.esprit.models.Questions;
import com.esprit.models.Quiz;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;


public class QuestionsService implements IService<Questions> {
    private Connection connection;
    private QuizService quizService;


    public QuestionsService() {
        connection = DataSource.getInstance().getConnection();
        quizService = new QuizService();
    }
    @Override
    public void ajouter(Questions questions) {
        String req = "INSERT INTO questions_quiz (quizId, texte, type, points, ordre, categorie) VALUES (" +
                questions.getQuiz().getQuizId() + ", '" + questions.getTexte() + "', '" + questions.getType() + "', " +
                questions.getPoints() + ", " + questions.getOrdre() + ", '" + questions.getCategorie() + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Question ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Questions questions) {
        String req = "UPDATE questions_quiz SET quizId = " + questions.getQuiz().getQuizId() +
                ", texte = '" + questions.getTexte() +
                "', type = '" + questions.getType() +
                "', points = " + questions.getPoints() +
                ", ordre = " + questions.getOrdre() +
                ", categorie = '" + questions.getCategorie() +
                "' WHERE questionID = " + questions.getQuestionId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Question modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Questions questions) {
        String req = "DELETE FROM questions_quiz WHERE questionId = " + questions.getQuestionId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Question supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Questions> afficher() {
        List<Questions> questionsList = new ArrayList<>();
        String req = "SELECT * FROM questions_quiz";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);

            while (rs.next()) {
                Quiz quiz = quizService.getQuiz(rs.getInt("quizId"));
                questionsList.add(new Questions(rs.getInt("questionId"),
                        quiz,
                        rs.getString("texte"),
                        rs.getString("type"),
                        rs.getInt("points"),
                        rs.getInt("ordre"),
                        rs.getString("categorie")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return questionsList;

    }

    public List<Questions> afficherQuestionsQuiz(int quizId) {
        List<Questions> questionsList = new ArrayList<>();
        String req = "SELECT * FROM questions_quiz WHERE quizId = " + quizId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Quiz quiz = quizService.getQuiz(rs.getInt("quizId"));
                questionsList.add(new Questions(rs.getInt("questionId"),
                        quiz,
                        rs.getString("texte"),
                        rs.getString("type"),
                        rs.getInt("points"),
                        rs.getInt("ordre"),
                        rs.getString("categorie")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return questionsList;

    }
    public Questions getQuestion(int questionId) {
        Questions question = null;
        String req = "SELECT * FROM questions_quiz WHERE questionId = " + questionId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            if (rs.next()) {
                Quiz quiz = quizService.getQuiz(rs.getInt("quizId"));
                question = new Questions(rs.getInt("questionId"),
                        quiz,
                        rs.getString("texte"),
                        rs.getString("type"),
                        rs.getInt("points"),
                        rs.getInt("ordre"),
                        rs.getString("categorie"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return question;
    }
}
