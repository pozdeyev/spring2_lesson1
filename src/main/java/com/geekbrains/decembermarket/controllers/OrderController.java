package com.geekbrains.decembermarket.controllers;
import com.geekbrains.decembermarket.beans.Cart;
import com.geekbrains.decembermarket.entites.Order;
import com.geekbrains.decembermarket.entites.User;
import com.geekbrains.decembermarket.services.MailService;
import com.geekbrains.decembermarket.services.OrderService;
import com.geekbrains.decembermarket.services.UserService;
import com.geekbrains.decembermarket.utils.PhoneEmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private UserService userService;
    private OrderService orderService;
    private Cart cart;
    private MailService mailService;
    List<String> errormessagelist = new ArrayList<>();

    public OrderController(UserService userService, OrderService orderService,MailService mailService, Cart cart) {
        this.userService = userService;
        this.orderService = orderService;
        this.mailService=mailService;
        this.cart = cart;
    }

    @GetMapping("/info")
    public String showOrderInfo(Model model, Principal principal) {
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("cart", cart);
        model.addAttribute("def_phone", user.getPhone());
        model.addAttribute("def_name",user.getFirstName());
        return "order_message";
    }


    @PostMapping("/create")
    public String createOrder(Principal principal, Model model, @RequestParam(name = "address") String address, @RequestParam("phone") String phone) {

        User user = userService.findByPhone(principal.getName());
        errormessagelist.clear();

        if (!PhoneEmailValidator.checkTelNumber(phone)) {
            errormessagelist.add("Проверьте корректность номера телефона");
        }
        if ("".equals(address)) {
            errormessagelist.add("Не указан адрес");
        }
        if (!errormessagelist.isEmpty()) {
            model.addAttribute("cart", cart);
            model.addAttribute("def_phone", user.getPhone());
            model.addAttribute("def_name",user.getFirstName());
            model.addAttribute("errormessagelist", errormessagelist);
            return "order_message";
        }


        Order order = new Order(user, cart, address, phone);
        order = orderService.save(order);

        //проверка и отправка почты с заказом
        if (user.getEmail() != null){
            System.out.println("отправка почта: "+user.getEmail());
            mailService.sendOrderMail(order);
        }

        model.addAttribute("order_id_str", String.format("%04d", order.getId()));
        model.addAttribute("order_id", order.getId());
        return "order_confirm";
    }

}
