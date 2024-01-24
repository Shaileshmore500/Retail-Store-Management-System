package com.sbs.SmartBillingSystem.Helper;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailHelper {




    

    
    
//     // @Autowired
//     // public EmailHelper(JavaMailSender javaMailSender) {
//     //     this.javaMailSender = javaMailSender;
//     // }
     
//     public boolean sendMail(String subject, String body, byte[] attachment, String receiver, String filename) throws jakarta.mail.MessagingException {

//         try {
//             jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();

//         Path resourcesPath = Paths.get("src", "main", "resources", "static", "images", "mailimg", filename).toAbsolutePath();

//         MimeMessageHelper helper = new MimeMessageHelper(message, true);

//         helper.setTo(receiver);
//         helper.setSubject(subject);
//         helper.setText(body);

//         // Add attachment
//         FileSystemResource file = new FileSystemResource(new File(resourcesPath.toString()));
//         String fname = file.getFilename();
//         helper.addAttachment(fname, file);
//         javaMailSender.send(message);

            
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
        
//         return true;





// //  jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();

        
 
 
// //  MimeMessage mimeMessage = javaMailSender.createMimeMessage();
// //         MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

// //         helper.setFrom(javaMailSender.getUsername());
// //         helper.setTo(to);
// //         helper.setSubject(subject);
// //         helper.setText(text);

// //         // Attach the file
// //         File file = new File(attachmentPath);
// //         helper.addAttachment("AttachmentName", file);

// //         javaMailSender.send(mimeMessage);
 
 
 
// //   Path resourcesPath = Paths
// //             .get("src", "main", "resources", "static", "images", "mailimg",filename)
// //             .toAbsolutePath();
 
 
// //  MimeMessageHelper helper = new MimeMessageHelper(message, true);

// //         helper.setTo(receiver);
// //         helper.setSubject(subject);
// //         helper.setText(body);

// //         // Add attachment
// //         FileSystemResource file = new FileSystemResource(new File(resourcesPath.toString()));
// //         String fname = file.getFilename();
// //         helper.addAttachment(fname, file);
// //         javaMailSender.send(message);




//         // // Sender's email address and password
//         // String senderEmail = "shmore500@gmail.com";
//         // String senderPassword = "parumore23";

//         // // Recipient's email address
//         // String recipientEmail = receiver;

//         // // Mail server properties (using Jakarta Mail package names)
//         // Properties properties = new Properties();
//         // properties.put("mail.smtp.host", "smtp.gmail.com");
//         // properties.put("mail.smtp.port", "587");
//         // properties.put("mail.smtp.auth", "true");
//         // properties.put("mail.smtp.starttls.enable", "true");

//         // // Create session using Jakarta Mail Session
//         // Session session = Session.getDefaultInstance(properties);

//         // try {
//         // // Create MimeMessage object
//         // Message message = new MimeMessage(session);

//         // // Set sender and recipient addresses
//         // message.setFrom(new InternetAddress(senderEmail));
//         // message.setRecipients(Message.RecipientType.TO,
//         // InternetAddress.parse(recipientEmail));

//         // // Set email subject
//         // message.setSubject(subject);

//         // // Create MimeBodyPart for the text content
//         // BodyPart textPart = new MimeBodyPart();
//         // textPart.setText(body);

//         // // Create MimeBodyPart for the attachment
//         // MimeBodyPart attachmentPart = new MimeBodyPart();

//         // // Replace 'fileContentByteArray' with the actual byte array representing the
//         // file content
//         // byte[] fileContentByteArray =attachment; //"Your file content as byte
//         // array".getBytes();
//         // DataSource source = new ByteArrayDataSource(fileContentByteArray,
//         // "application/octet-stream");
//         // attachmentPart.setDataHandler(new DataHandler(source));
//         // attachmentPart.setFileName(filename); // Replace with the actual file name

//         // // Create Multipart and add parts to it
//         // Multipart multipart = new MimeMultipart();
//         // multipart.addBodyPart(textPart);
//         // multipart.addBodyPart(attachmentPart);

//         // // Set the Multipart object as the content of the message
//         // message.setContent(multipart);

//         // // Send the message using Jakarta Mail Transport
//         // Transport.send(message);

//         // System.out.println("Email sent successfully!");

//     }
//      //java configuration bean
//     @Bean
//     public JavaMailSender javaMailSender() {
//         JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

//         mailSender.setHost("smtp.gmail.com");
//         mailSender.setPort(587); // Use your mail server port
//         mailSender.setUsername("shmore500@gmail.com");
//         mailSender.setPassword("parumore23");

//         // Set additional properties
//         mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
//         mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");

//         return mailSender;
//     }
}
