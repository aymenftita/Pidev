package com.esprit.services;

import com.esprit.models.Group;
import com.esprit.models.Groupe;
import com.esprit.models.Message;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupService {

    private final Connection connection;

    public GroupService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void modifier(Group groupe) {
        String req = "UPDATE groupe set creator_id = '" + groupe.getIdUser() + "',titre = '" + groupe.getTitre() + "', description = '" + groupe.getDescription() + "' where id_groupe = " + groupe.getIdGroup() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("message modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
