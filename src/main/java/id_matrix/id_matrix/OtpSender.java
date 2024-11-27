package id_matrix.id_matrix;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import javafx.scene.control.Alert;

import java.util.Properties;

public class OtpSender {

    public Boolean otp(String email, String otp) {
        // Sender's email credentials

        String from = "loneribrahim@gmail.com";
      //  String password="dbxn dgop osms ilf";
         String password = "dvjl fxuu pzwc bhet";

        // Recipient's email address
        String to = email;

        // SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");  // SMTP Host
        properties.put("mail.smtp.port", "587");            // TLS Port

        properties.put("mail.smtp.auth", "true");           // Enable authentication
        properties.put("mail.smtp.starttls.enable", "true");// Enable StartTLS

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create a MimeMessage
            Message message = new MimeMessage(session);

            // Set From field
            message.setFrom(new InternetAddress(from));

            // Set To field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject
            message.setSubject("OTP");

            // Set the message body
            message.setText(otp);

            // Send the message
            Transport.send(message);

            showAlert("Success", "Otp sent to " + to);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();

            showAlert("Error", "Otp not sent. Please make sure your email is correct.");
            return false;

        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

