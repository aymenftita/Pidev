package com.esprit.services.ReclamationGroupServices;

import com.esprit.models.ReclamationGroupModels.Groupe;
import com.esprit.models.ReclamationGroupModels.UserGroupe;
import com.esprit.models.ReclamationGroupModels.Utilisateur;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserGroupeService {

    private Connection connection;

    public  UserGroupeService() {connection = DataSource.getInstance().getConnection();}

    public void ajouter(int idU , int idG) {

        String req = "INSERT INTO utilisateurs_groupes(uid,groupe_id) VALUES ('"+ idU +"','"+idG+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("User_Groupe ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<UserGroupe> ListUserGroupeByIdUser(int id) {
        List<UserGroupe> entities = new ArrayList<>();

        String req = "SELECT * from utilisateurs_groupes where uid = "+ id +";";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            UtilisateurService us =new UtilisateurService();
            GroupeService gs = new GroupeService();
            while (rs.next()) {
                Utilisateur u = us.rechercheUtilisateur(rs.getInt(1));
                Groupe g = gs.afficherById(rs.getInt(2));
                UserGroupe ug = new UserGroupe(u,g);
                entities.add(ug);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;

    }
    public UserGroupe afficherUG(int id,int idg) {
        UserGroupe ug =new UserGroupe();
        String req = "SELECT * from utilisateurs_groupes where uid ="+id+" and groupe_id ="+idg+";";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            UtilisateurService us =new UtilisateurService();
            GroupeService gs =new GroupeService();
            while (rs.next()) {
                Utilisateur user = us.rechercheUtilisateur( rs.getInt(1));
                Groupe g = gs.afficherById(rs.getInt(2));
                ug.setIdUser(user);
                ug.setIdGroupe(g);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ug;

    }
}
