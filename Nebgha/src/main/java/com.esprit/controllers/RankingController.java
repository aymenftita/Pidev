package com.esprit.controllers;

import com.esprit.models.Questions;
import com.esprit.models.ReponsesUtilisateur;
import com.esprit.models.Role;
import com.esprit.models.Utilisateur;
import com.esprit.services.QuestionsService;
import com.esprit.services.ReponsesUtilisateurService;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RankingController implements Initializable {


    @FXML
    private VBox topUsersVBox;

    private UtilisateurService utilisateurService;
    private ReponsesUtilisateurService reponsesUtilisateurService;
    private QuestionsService questionsService;
    @FXML
    private Label firstPlaceLabel;

    @FXML
    private Label secondPlaceLabel;

    @FXML
    private Label thirdPlaceLabel;
    private Utilisateur user =Session.getCurrentUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utilisateurService = new UtilisateurService();
        reponsesUtilisateurService = new ReponsesUtilisateurService();
        questionsService = new QuestionsService();

        int currentUserId = user.getId();
        Utilisateur currentUser = utilisateurService.getUser(currentUserId);

        List<Utilisateur> allUsers = utilisateurService.afficher();
        List<Utilisateur> students = allUsers.stream()
                .filter(user->user.getRole().equals(Role.Etudiant)).toList();
        Map<Utilisateur, Integer> userScores = new HashMap<>();

        for (Utilisateur utilisateur : students) {
            List<ReponsesUtilisateur> reponsesUtilisateurList = reponsesUtilisateurService.afficherParUser(utilisateur.getId());
            int totalScore = reponsesUtilisateurList.stream()
                    .mapToInt(reponseUtilisateur -> {
                        int questionId = reponseUtilisateur.getReponse().getQuestion().getQuestionId();
                        Questions question = questionsService.getQuestion(questionId);
                        return reponseUtilisateur.isCorrect() ? question.getPoints() : 0;
                    })
                    .sum();

            userScores.put(utilisateur, totalScore);
        }

        List<Map.Entry<Utilisateur, Integer>> sortedScores = userScores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toList());

        for (int i = 0; i < Math.min(sortedScores.size(), 3); i++) {
            Map.Entry<Utilisateur, Integer> entry = sortedScores.get(i);
            if (i == 0) {
                firstPlaceLabel.setText(entry.getKey().getNom());
            } else if (i == 1) {
                secondPlaceLabel.setText(entry.getKey().getNom());
            } else if (i == 2) {
                thirdPlaceLabel.setText(entry.getKey().getNom());
            }
        }

        boolean currentUserInTop5 = false;
        for (Map.Entry<Utilisateur, Integer> entry : sortedScores) {
            if (entry.getKey().getId() == currentUserId) {
                currentUserInTop5 = true;
                break;
            }
        }

        if (!currentUserInTop5) {
            int currentUserScore = userScores.getOrDefault(currentUser, 0);
            Map.Entry<Utilisateur, Integer> currentUserEntry = new AbstractMap.SimpleEntry<>(currentUser, currentUserScore);
            sortedScores.add(currentUserEntry);
            sortedScores = sortedScores.stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(5)
                    .collect(Collectors.toList());
        }

        for (int i = 0; i < sortedScores.size(); i++) {
            Map.Entry<Utilisateur, Integer> entry = sortedScores.get(i);
            ImageView medalImageView = new ImageView(new Image(getClass().getResourceAsStream("/media/" + (i + 1) + ".png")));
            medalImageView.setFitHeight(30);
            medalImageView.setFitWidth(30);

            Label nameLabel = new Label(entry.getKey().getNom());
            nameLabel.getStyleClass().add("reward-name-label");

            Label scoreLabel = new Label("Score : " + String.valueOf(entry.getValue()));
            scoreLabel.getStyleClass().add("reward-score-label");

            HBox hbox = new HBox();
            hbox.getStyleClass().add("reward-block");
            hbox.getChildren().addAll(medalImageView, nameLabel);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            hbox.getChildren().add(spacer);

            hbox.getChildren().add(scoreLabel);

            topUsersVBox.getChildren().add(hbox);
        }


    }

    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecompensesEtudiant.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("My rewards");
        currentStage.show();
    }
}
