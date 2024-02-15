package com.esprit.services;

import com.esprit.models.Groupe;
import com.esprit.models.Message;
import com.esprit.models.Reclamation;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private Connection connection;

    public  MessageService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(Message message) {
        String req = "INSERT into message(" +
                "id_g, " +
                "uid, " +
                "uid," +
                "date_creation)" +
                "values ('" +
                message.getId_g() + ", " +
                message.getUid() + ", " +
                message.getDate_creation() + ", " +
                message.getText() +
                "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(Message message) {
        String req = "UPDATE message set text = '" + message.getText() + "', date_creation = '" + message.getDate_creation() + "' where id_message = " + message.getId_message() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("message modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(Message message) {
        String req = "DELETE from message where id_message = " + message.getId_message() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("entité supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void afficher() {
        List<Message> entities = new ArrayList<>();

        String req = "SELECT * from message";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                entities.add(new Message(rs.getInt("id_messsage"), rs.getInt("uid"), rs.getString("id_g") , rs.getString("Text")));

            }
            System.out.println(entities.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
