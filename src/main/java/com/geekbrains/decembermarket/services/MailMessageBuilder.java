package com.geekbrains.decembermarket.services;

import com.geekbrains.decembermarket.entites.Order;
import com.geekbrains.decembermarket.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailMessageBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    //Формируем письмо на подтверждение электронной почты
    public String buildOrderEmail(Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        return templateEngine.process("order-mail", context);
    }

    //Формируем письмо на подтверждение электронного адреса
    public String buildApproveEmail(User user)
    {
        Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process("email_approve-mail", context);
    }

}
