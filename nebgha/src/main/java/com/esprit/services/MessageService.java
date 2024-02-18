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

       String req = "INSERT INTO message(id_g,text,date_creation) VALUES ('"+message.getIdGroupe()+"','"+message.getText()+"','"+message.getDateCreation()+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(Message message) {
        String req = "UPDATE message set text = '" + message.getText() + "', date_creation = '" + message.getDateCreation() + "' where id_g = " + message.getIdGroupe() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("message modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(Message message) {
        String req = "DELETE from message where id_message = " + message.getIdMessage() + ";";
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
                entities.add(new Message(rs.getInt("id_message"),rs.getInt("id_g") , rs.getString("date_creation"),  rs.getString("text")));

            }
            System.out.println(entities.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
