package property.management;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    private static final String SENDER_EMAIL = System.getenv("EMAIL_USERNAME");
    private static final String APP_PASSWORD = System.getenv("EMAIL_PASSWORD");


    public static void sendOTP(String recipientEmail, String otp) {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
                properties,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                SENDER_EMAIL,
                                APP_PASSWORD
                        );
                    }
                }
        );

        try {

            Message message =
                    new MimeMessage(session);

            message.setFrom(
                    new InternetAddress(SENDER_EMAIL)
            );

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );

            message.setSubject(
                    "Property Management System - OTP"
            );

            message.setText(
                    "Your OTP is: " + otp +
                            "\n\nThis OTP is valid for 10 minutes." +
                            "\n\nIf you did not request this OTP, please ignore this email."
            );

            Transport.send(message);

            System.out.println(
                    "OTP sent successfully to " + recipientEmail
            );

        } catch (MessagingException e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to send OTP email."
            );
        }
    }
}