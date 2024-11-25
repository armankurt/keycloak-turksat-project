package com.example.keycloak;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.jboss.logging.Logger;

public class EmailSender {

    private static final Logger logger = Logger.getLogger(EmailSender.class);

    public static void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465"); // SSL için doğru port
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS kullanıyorsanız bu gerekli
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Gmail sertifikasını güvenilir olarak ekleyin

        String username = "practicaltwist@gmail.com"; // Your email
        String password = "uygulama_sifresi"; // Gmail uygulama şifresi

        logger.info("E-posta gönderimi başlatılıyor: " + to);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message); // Send the email
            logger.info("E-posta başarıyla gönderildi: " + to);

        } catch (MessagingException e) {
            logger.error("E-posta gönderimi sırasında hata oluştu", e);
        }
    }
}
