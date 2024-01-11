package com.sbs.SmartBillingSystem.Helper;

import java.util.Properties;

public class EmailHelper {

    public static boolean sendMail(String subject, String body, byte[] attachment, String receiver, String filename) {

        // // Sender's email address and password
        // String senderEmail = "shmore500@gmail.com";
        // String senderPassword = "parumore23";

        // // Recipient's email address
        // String recipientEmail = receiver;

        // // Mail server properties (using Jakarta Mail package names)
        // Properties properties = new Properties();
        // properties.put("mail.smtp.host", "smtp.gmail.com");
        // properties.put("mail.smtp.port", "587");
        // properties.put("mail.smtp.auth", "true");
        // properties.put("mail.smtp.starttls.enable", "true");

        // // Create session using Jakarta Mail Session
        // Session session = Session.getDefaultInstance(properties);

        // try {
        // // Create MimeMessage object
        // Message message = new MimeMessage(session);

        // // Set sender and recipient addresses
        // message.setFrom(new InternetAddress(senderEmail));
        // message.setRecipients(Message.RecipientType.TO,
        // InternetAddress.parse(recipientEmail));

        // // Set email subject
        // message.setSubject(subject);

        // // Create MimeBodyPart for the text content
        // BodyPart textPart = new MimeBodyPart();
        // textPart.setText(body);

        // // Create MimeBodyPart for the attachment
        // MimeBodyPart attachmentPart = new MimeBodyPart();

        // // Replace 'fileContentByteArray' with the actual byte array representing the
        // file content
        // byte[] fileContentByteArray =attachment; //"Your file content as byte
        // array".getBytes();
        // DataSource source = new ByteArrayDataSource(fileContentByteArray,
        // "application/octet-stream");
        // attachmentPart.setDataHandler(new DataHandler(source));
        // attachmentPart.setFileName(filename); // Replace with the actual file name

        // // Create Multipart and add parts to it
        // Multipart multipart = new MimeMultipart();
        // multipart.addBodyPart(textPart);
        // multipart.addBodyPart(attachmentPart);

        // // Set the Multipart object as the content of the message
        // message.setContent(multipart);

        // // Send the message using Jakarta Mail Transport
        // Transport.send(message);

        // System.out.println("Email sent successfully!");

        // } catch (MessagingException e) {
        // e.printStackTrace();
        // }
        return true;
    }
}
