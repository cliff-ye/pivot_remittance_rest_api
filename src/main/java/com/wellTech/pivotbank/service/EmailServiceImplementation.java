package com.wellTech.pivotbank.service;

import com.wellTech.pivotbank.dto.EmailDetailsDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImplementation implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    //global access to the sender email
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmail(EmailDetailsDTO emailDetailsDTO) {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetailsDTO.recipient());
            simpleMailMessage.setText(emailDetailsDTO.messageBody());
            simpleMailMessage.setSubject(emailDetailsDTO.subject());

            javaMailSender.send(simpleMailMessage);
            System.out.println("mail sent");
        }catch (MailException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailwithPdf(EmailDetailsDTO emailDetailsDTO) {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper;

            try{
                mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(senderEmail);
                mimeMessageHelper.setTo(emailDetailsDTO.recipient());
                mimeMessageHelper.setText(emailDetailsDTO.messageBody());
                mimeMessageHelper.setSubject(emailDetailsDTO.subject());

                FileSystemResource file = new FileSystemResource(new File(emailDetailsDTO.attachment()));
                mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

                javaMailSender.send(mimeMessage);
                log.info("file sent to "+emailDetailsDTO.recipient());
            }
        catch (MailException e){
            throw new RuntimeException(e);
        } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
    }
}
