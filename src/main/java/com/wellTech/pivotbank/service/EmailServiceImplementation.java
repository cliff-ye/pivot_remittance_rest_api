package com.wellTech.pivotbank.service;

import com.wellTech.pivotbank.dto.EmailDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
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
}
