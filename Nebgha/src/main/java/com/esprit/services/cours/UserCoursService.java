package com.esprit.services.cours;

import com.esprit.models.cours.Cours;
import com.esprit.models.cours.UserCours;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserCoursService {

    private Connection connection;
    private Cours entityManager;

    public UserCoursService() {
        connection = DataSource.getInstance().getConnection();}


    public List<UserCours> getCoursByUseId(int uid) {
        String req = "SELECT * FROM user_cours WHERE uid = " + uid + ";";
        List<UserCours> ucList = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while(rs.next()) {


                int id = rs.getInt("uid");

                int Cid = rs.getInt("Cid");

                ucList.add(new UserCours(id, Cid)) ;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ucList;
    }

    public void ajouter(UserCours userCours) {
        String req = "INSERT INTO user_cours (uid,Cid) VALUES ('" + userCours.getUid() +   "', " + userCours.getCid() + ")";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("user_cours ajoutée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du user_cours : " +e.getMessage());
        }


    }
}
