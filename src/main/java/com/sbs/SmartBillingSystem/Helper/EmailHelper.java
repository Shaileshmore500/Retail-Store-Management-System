// package com.sbs.SmartBillingSystem.Helper;

// import java.maimail.*;
// import javax.mail.internet.*;
// import javax.activation.*;
// import java.util.Properties;

// public class EmailHelper {

//     public static boolean sendMail()
//     {
//      // Sender's email address
//         String from = "your_email@gmail.com";

//         // Recipient's email address
//         String to = "recipient_email@example.com";

//         // SMTP server settings (replace with your SMTP server details)
//         String host = "smtp.gmail.com";
//         String username = "your_email@gmail.com";
//         String password = "your_email_password";

//         // Create properties for the Session
//         Properties properties = System.getProperties();
//         properties.setProperty("mail.smtp.host", host);
//         properties.setProperty("mail.smtp.port", "587");
//         properties.setProperty("mail.smtp.auth", "true");
//         properties.setProperty("mail.smtp.starttls.enable", "true");

//         // Create a Session object with the specified properties and authenticator
//         Session session = Session.getInstance(properties, new Authenticator() {
//             protected PasswordAuthentication getPasswordAuthentication() {
//                 return new PasswordAuthentication(username, password);
//             }
//         });

//         try {
//             // Create a MimeMessage object
//             MimeMessage message = new MimeMessage(session);

//             // Set the sender and recipient addresses
//             message.setFrom(new InternetAddress(from));
//             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

//             // Set the subject
//             message.setSubject("Email with Attachment");

//             // Create the message body
//             BodyPart messageBodyPart = new MimeBodyPart();
//             messageBodyPart.setText("This is the message body.");

//             // Create the attachment
//             MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//             String attachmentFilePath = "path/to/your/file.txt";
//             DataSource source = new FileDataSource(attachmentFilePath);
//             attachmentBodyPart.setDataHandler(new DataHandler(source));
//             attachmentBodyPart.setFileName("attachment.txt");

//             // Create a Multipart object to hold the message body and attachment
//             Multipart multipart = new MimeMultipart();
//             multipart.addBodyPart(messageBodyPart);
//             multipart.addBodyPart(attachmentBodyPart);

//             // Set the Multipart object as the message's content
//             message.setContent(multipart);

//             // Send the message
//             Transport.send(message);
//             System.out.println("Email sent successfully.");

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
    
// }
