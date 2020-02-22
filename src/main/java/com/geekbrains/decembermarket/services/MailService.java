package com.geekbrains.decembermarket.services;

import com.geekbrains.decembermarket.entites.Order;
import com.geekbrains.decembermarket.entites.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private JavaMailSender sender;
    private MailMessageBuilder messageBuilder;

    private Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    @Autowired
    public void setMessageBuilder(MailMessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    private void sendMail(String email, String subject, String text, String token) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(email);
        helper.setText(text, true);
        helper.setSubject(subject);
        sender.send(message);
    }

    public void sendOrderMail(Order order) {
        try {
            sendMail(order.getUser().getEmail(), String.format("Заказ %d%n отправлен в обработку", order.getId()), messageBuilder.buildOrderEmail(order),"");
        } catch (MessagingException e) {
            logger.warn("Unable to create order mail message for order: " + order.getId());
        } catch (MailException e) {
            logger.warn("Unable to send order mail message for order: " + order.getId());
        }
    }

    public void  sendApproveMail (User user){
        try {
            sendMail(user.getEmail(), String.format(user.getFirstName() +", подтвердите Ваш электронный адрес"),
                    messageBuilder.buildApproveEmail(user), user.getEmail_token());
        } catch (MessagingException e) {
            logger.warn("Unable to create order mail message for user: " + user.getId());
        } catch (MailException e) {
            logger.warn("Unable to send order mail message for user: " + user.getId());
        }

    }

}