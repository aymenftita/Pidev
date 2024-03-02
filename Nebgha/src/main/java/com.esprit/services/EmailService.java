package com.esprit.services;

import com.esprit.models.Questions;
import com.esprit.models.ReponsesUtilisateur;
import jakarta.mail.*;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class EmailService {

    public static void sendResultsByEmail(String email, String quizTitle, int correctAnswers, int totalQuestions, double score, List<Questions> questions, List<ReponsesUtilisateur> reponsesUtilisateurList) {
        // SMTP server configuration
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Email credentials
        String username = "layouni.nourelhouda@gmail.com";
        String password = "pqxe rupi fbkm wdbq";

        // Sender's email address
        String fromEmail = "layouni.nourelhouda@gmail.com";

        // Recipient's email address
        String toEmail = email;

        // Email subject
        String subject = "Quiz Results - " + quizTitle;

        // Email content
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

        // Replace placeholders in the template with actual data
        content.replace(content.indexOf("{quizTitle}"), content.indexOf("{quizTitle}") + "{quizTitle}".length(), quizTitle);
        content.replace(content.indexOf("{correctAnswers}"), content.indexOf("{correctAnswers}") + "{correctAnswers}".length(), String.valueOf(correctAnswers));
        content.replace(content.indexOf("{score}"), content.indexOf("{score}") + "{score}".length(), String.format("%.1f", score) + "%\n\n");
        content.replace(content.indexOf("{totalQuestions}"), content.indexOf("{totalQuestions}") + "{totalQuestions}".length(), String.valueOf(totalQuestions));


        // Append questions and answers
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
/*<td align="center" class="es-m-txt-c" style="padding:0;Margin:0;padding-top:20px;padding-bottom:20px"><h3 style="Margin:0;line-height:26px;mso-line-height-rule:exactly;font-family:'Concert One', Arial, sans-serif;font-size:22px;font-style:normal;font-weight:normal;color:#34297C"><a target="_blank" style="-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#34297c;font-size:22px" href="">Here are your - {quizTitle} - quiz results :</a></h3><br><h3 style="Margin:0;line-height:26px;mso-line-height-rule:exactly;font-family:'Concert One', Arial, sans-serif;font-size:22px;font-style:normal;font-weight:normal;color:#34297C"><a target="_blank" style="-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#34297c;font-size:22px" href="">Correct answers : {correctAnswers}</a></h3><br>
 <h3 style="Margin:0;line-height:26px;mso-line-height-rule:exactly;font-family:'Concert One', Arial, sans-serif;font-size:22px;font-style:normal;font-weight:normal;color:#34297C"><a target="_blank" style="-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#34297c;font-size:22px" href="">Your score : {score}</a></h3>
 <h3 style="Margin:0;line-height:26px;mso-line-height-rule:exactly;font-family:'Concert One', Arial, sans-serif;font-size:22px;font-style:normal;font-weight:normal;color:#34297C"><a target="_blank" style="-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#34297c;font-size:22px" href="">Total questions : {totalQuestions}</a></h3><br><h3 style="Margin:0;line-height:26px;mso-line-height-rule:exactly;font-family:'Concert One', Arial, sans-serif;font-size:22px;font-style:normal;font-weight:normal;color:#34297C"><a target="_blank" style="-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#34297c;font-size:22px" href="">{questionsAndAnswers}</a></h3><h3 style="Margin:0;line-height:26px;mso-line-height-rule:exactly;font-family:'Concert One', Arial, sans-serif;font-size:22px;font-style:normal;font-weight:normal;color:#34297C"><a target="_blank" href="" style="-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#FFFFFF;font-size:22px"></a></h3></td></tr>*/
// Replace the {questionsAndAnswers} placeholder with the actual questions and answers
        content.replace(content.indexOf("{questionsAndAnswers}"), content.indexOf("{questionsAndAnswers}") + "{questionsAndAnswers}".length(), questionsAndAnswers.toString());


        // Send email
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
