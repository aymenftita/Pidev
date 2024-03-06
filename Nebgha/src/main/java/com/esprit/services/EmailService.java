package com.esprit.services;

import com.esprit.models.Questions;
import com.esprit.models.ReponsesUtilisateur;
import jakarta.mail.*;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class EmailService {

    public static void sendResultsByEmail(String email, String quizTitle, int correctAnswers, int totalQuestions, double score, List<Questions> questions, List<ReponsesUtilisateur> reponsesUtilisateurList) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        String username = "layouni.nourelhouda@gmail.com";
        String password = "pqxe rupi fbkm wdbq";

        String fromEmail = "layouni.nourelhouda@gmail.com";

        String toEmail = email;

        String subject = "Quiz Results - " + quizTitle;

        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/media/QuizEmailTemplate.html"));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading email template file: " + e.getMessage());
            return;
        }

        content.replace(content.indexOf("{quizTitle}"), content.indexOf("{quizTitle}") + "{quizTitle}".length(), quizTitle);
        content.replace(content.indexOf("{correctAnswers}"), content.indexOf("{correctAnswers}") + "{correctAnswers}".length(), String.valueOf(correctAnswers));
        content.replace(content.indexOf("{score}"), content.indexOf("{score}") + "{score}".length(), String.format("%.1f", score) + "%\n\n");
        content.replace(content.indexOf("{totalQuestions}"), content.indexOf("{totalQuestions}") + "{totalQuestions}".length(), String.valueOf(totalQuestions));


        StringBuilder questionsAndAnswers = new StringBuilder();
        questionsAndAnswers.append("Questions and Answers:<br> <br>");
        for (Questions question : questions) {
            questionsAndAnswers.append("Question: ").append(question.getTexte()).append("<br>");
            for (ReponsesUtilisateur reponsesUtilisateur : reponsesUtilisateurList) {
                if (reponsesUtilisateur.getReponse().getQuestion().getQuestionId() == question.getQuestionId()) {
                    String answerText = reponsesUtilisateur.getReponse().getTexte();
                    String status = reponsesUtilisateur.isCorrect() ? "Correct" : "Incorrect";
                    questionsAndAnswers.append("Answer: ").append(answerText).append(" - ").append(status).append("<br>");
                }
            }
            questionsAndAnswers.append("<br>");
        }

        content.replace(content.indexOf("{questionsAndAnswers}"), content.indexOf("{questionsAndAnswers}") + "{questionsAndAnswers}".length(), questionsAndAnswers.toString());


        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setContent(content.toString(), "text/html");

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
