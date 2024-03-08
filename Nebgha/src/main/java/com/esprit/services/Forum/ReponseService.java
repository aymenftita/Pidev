package com.esprit.services.Forum;

import com.esprit.models.Forum.Question;
import com.esprit.services.IService;
import com.esprit.services.utilisateur.ServiceUtilisateur;
import com.esprit.utils.DataSource;
import com.esprit.models.Forum.Reponse;

import java.sql.*;
import java.util.*;

public class ReponseService implements IService<Reponse> {


    private Connection connection;

    public ReponseService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reponse reponse) {
        String req = "INSERT into reponse_forum(auteur_id, question_id, contenu, date_creation, sujet_id) values ('" +
                reponse.getAuteur().getId() + "', '"
                + reponse.getQuestion().getId() + "', '" + reponse.getContenu() + "', '" + reponse.getDate() +
                "', "+ reponse.getSujet().getId() + ");";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reponse reponse) {
        String req = "UPDATE reponse_forum set auteur_id = " + reponse.getAuteur().getId() + ", question_id = "
                + reponse.getQuestion().getId() + ", contenu = '" + reponse.getContenu()
                + "', date_creation = '" + reponse.getDate() + "', sujet_id = " + reponse.getSujet().getId() +
                " where id = " + reponse.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Reponse reponse) {
        String req = "DELETE from reponse_forum where id = " + reponse.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("réponse supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reponse> afficher() {
        List<Reponse> reponses = new ArrayList<>();

        String req = "SELECT * from reponse_forum";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            SujetService ss = new SujetService();//sujet service pour récupérer le sujet d'aprés l'ID
            QuestionService qs = new QuestionService();//question service pour récupérer le question d'aprés l'ID
            ServiceUtilisateur su = new ServiceUtilisateur();
            while (rs.next()) {
                reponses.add(new Reponse(rs.getInt("id"), su.getUser(rs.getInt("auteur_id")),
                        qs.getQuestion(rs.getInt("question_id")), rs.getString("contenu"),
                        rs.getDate("date_creation"), ss.getSujet(rs.getInt("sujet_id")),
                        rs.getInt("score"), rs.getBoolean("accept_status"),
                        rs.getBoolean("report_status")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reponses;
    }

    public List<Reponse> afficherParQuestion(Question question) {
        List<Reponse> reponses = new ArrayList<>();

        String req = "SELECT * from reponse_forum WHERE question_id =" + question.getId();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            SujetService ss = new SujetService();//sujet service pour récupérer le sujet d'aprés l'ID
            QuestionService qs = new QuestionService();//question service pour récupérer le question d'aprés l'ID
            ServiceUtilisateur su = new ServiceUtilisateur();
            while (rs.next()) {
                reponses.add(new Reponse(rs.getInt("id"), su.getUser(rs.getInt("auteur_id")),
                        qs.getQuestion(rs.getInt("question_id")), rs.getString("contenu"),
                        rs.getDate("date_creation"), ss.getSujet(rs.getInt("sujet_id")),
                        rs.getInt("score"), rs.getBoolean("accept_status"),
                        rs.getBoolean("report_status")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reponses;
    }

    public void upVote(Reponse reponse) {
        String req = "UPDATE reponse_forum set score = " + ( reponse.getScore() + 1 ) +
                " where id = " + reponse.getId() + ";";
        reponse.setScore(reponse.getScore()+1);
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse upVoted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void downVote(Reponse reponse) {
        String req = "UPDATE reponse_forum set score = " + ( reponse.getScore() - 1 ) +
                " where id = " + reponse.getId() + ";";

        reponse.setScore(reponse.getScore()-1);
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse downVoted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void acceptReponse(Reponse reponse) {
        String req = "UPDATE reponse_forum set accept_status = " + true +
                " where id = " + reponse.getId() + ";";
        reponse.setAccepted(true);
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse accepté!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void unAcceptReponse(Reponse reponse) {
        String req = "UPDATE reponse_forum set accept_status = " + false +
                " where id = " + reponse.getId() + ";";
        reponse.setAccepted(false);
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse un-accepté!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void reportReponse(Reponse reponse) {
        String req = "UPDATE reponse_forum set report_status = " + true +
                " where id = " + reponse.getId() + ";";
        reponse.setReported(true);
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse reporté!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void unreportReponse(Reponse reponse) {
        String req = "UPDATE reponse_forum set report_status = " + false +
                " where id = " + reponse.getId() + ";";
        reponse.setReported(false);
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse un-reporté!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean AiResponded(Question question) {
        int questionId = question.getId();
        String req = "SELECT EXISTS(SELECT * FROM reponse_forum WHERE auteur_id = 1 AND question_id = " + questionId + ")";
        try {
            Statement st = connection.createStatement();
            System.out.println("AI response check!");

            ResultSet resultSet = st.executeQuery(req);

            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;

    }

}