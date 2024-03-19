package com.sbs.SmartBillingSystem.Services.ServiceImpl;




import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbs.SmartBillingSystem.Services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendMail(MultipartFile file, String to, String[] cc, String subject, String body,byte[] arrbyte) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            //mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body==null ? "" : body);

            // for (int i = 0; i < file.length; i++) 
            // {
            //     mimeMessageHelper.addAttachment(
            //             file[i].getOriginalFilename(),
            //             new ByteArrayResource(file[i].getBytes()));
            // }
            if(file !=null)
            {
            mimeMessageHelper.addAttachment(
                             file.getOriginalFilename(),
                            new ByteArrayResource(file.getBytes()));
            }
            else if(arrbyte!=null)
            {
                mimeMessageHelper.addAttachment("Invoice.pdf", new ByteArrayResource(arrbyte));

            }
            javaMailSender.send(mimeMessage);

            return "mail send";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
