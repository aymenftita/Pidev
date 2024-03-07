package com.esprit.services.quiz;

import com.esprit.models.quiz.Quiz;
import com.esprit.models.quiz.ReponsesUtilisateur;
import com.esprit.services.Session;
import org.controlsfx.control.Notifications;

import java.time.LocalDate;
import java.util.List;

public class NotificationService {
    public void showInformationNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

    public void showWarningNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showWarning();
    }


    public void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

    public int calculateNumberOfTimesQuizPassedToday() {
        ReponsesUtilisateurService reponsesUtilisateurService = new ReponsesUtilisateurService();
        List<ReponsesUtilisateur> responses = reponsesUtilisateurService.afficher();
        int userId = Session.getCurrentUser().getId();;
        LocalDate today = LocalDate.now();
        System.out.println(today);
        System.out.println(responses);
        long quizzesPassedToday = responses.stream()
                .filter(response -> response.getQuiz().getCreator().getId() == userId)
                .filter(response -> response.getDate().toLocalDate().equals(today))
                .count();

        System.out.println(quizzesPassedToday);
        return (int) quizzesPassedToday;

}
    public int calculateNumberOfQuizzesAddedToday() {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();
        LocalDate today = LocalDate.now();
        long quizzesAddedToday = quizzes.stream()
                .filter(quiz -> quiz.getDateCreation().toLocalDate().equals(today))
                .count();

        System.out.println(quizzesAddedToday);
        return (int) quizzesAddedToday;

    }

    public void showQuizzesPassedTodayNotification() {
        int quizzesPassedToday = calculateNumberOfTimesQuizPassedToday();
        System.out.println(quizzesPassedToday);
        if (quizzesPassedToday > 0) {
            Notifications.create()
                    .title("Quizzes Passed Today")
                    .text("one of your quizzes have been passed "+ quizzesPassedToday + "times today")
                    .showInformation();
        }
    }
    public void showQuizzesAddedTodayNotification() {
        int quizzesAddedToday = calculateNumberOfQuizzesAddedToday();
        System.out.println(quizzesAddedToday);
        if (quizzesAddedToday > 0) {
            Notifications.create()
                    .title("Quizzes Added Today")
                    .text(quizzesAddedToday + " new quiz(zes) added today check them! ")
                    .showInformation();
        }
    }
    public static void showLoadingNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle()
                .showInformation();
    }

    public static void showSuccessNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle()
                .showInformation();
    }

    public static void showErrorNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .darkStyle()
                .showError();
    }
}
