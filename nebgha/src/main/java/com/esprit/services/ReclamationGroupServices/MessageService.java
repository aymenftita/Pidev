package com.esprit.services.ReclamationGroupServices;

import com.esprit.models.ReclamationGroupModels.Message;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private final Connection connection;

    public  MessageService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(Message message) {

       String req = "INSERT INTO message(uid,id_g,text,date_creation,signale) VALUES ('"+message.getUid()+"','"+message.getIdGroupe()+"','"+message.getText()+"','"+message.getDateCreation()+"','"+message.getSignal()+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Message ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifier(Message message) {
        String req = "UPDATE message set signale = '" + message.getSignal() + "', id_g = '" + message.getIdGroupe() + "',text = '" + message.getText() + "', date_creation = '" + message.getDateCreation() + "' where id_message = " + message.getIdMessage() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("message modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(int id) {
        String req = "DELETE from message where id_message = " + id + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Message supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Message> afficher() {
        List<Message> entities = new ArrayList<>();

        String req = "SELECT * from message";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                entities.add(new Message(rs.getInt("id_message"),rs.getInt("id_g"), rs.getString("date_creation"), rs.getString("text"),rs.getInt("uid"),0));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;
    }

    public List<Message> readByid(int id) {

        List<Message> categories = new ArrayList<>();

        String req = "SELECT * from message where id_g ="+id+";";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                categories.add(new Message(rs.getInt(1),rs.getInt(2), rs.getString(3), rs.getString(4),rs.getInt(5),0));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }
}
