package com.geekbrains.decembermarket.controllers;

import com.geekbrains.decembermarket.beans.Cart;
import com.geekbrains.decembermarket.entites.Order;
import com.geekbrains.decembermarket.entites.User;
import com.geekbrains.decembermarket.services.OrderService;
import com.geekbrains.decembermarket.services.UserService;
import com.geekbrains.decembermarket.utils.PhoneEmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/fastorders")
public class FastOrderController {
    private Cart cart;
    private UserService userService;
    private OrderService orderService;
    List<String> errormessagelist=new ArrayList<>();

    public FastOrderController(UserService userService, OrderService orderService, Cart cart) {
        this.cart = cart;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/fastinfo")
    public String showOrderFastInfo(Model model) {
        model.addAttribute("cart", cart);
        return "order_fastmessage";
    }


    @PostMapping("/create")
    public String createFastOrder(Model model, @RequestParam(name = "address") String address, @RequestParam("phone") String phone) {

        User existingPhone = userService.findByPhone(phone);
        errormessagelist.clear();
        if (existingPhone != null) {
            errormessagelist.add("Пользователь с этим номером телефона существует. Пожалуйста,войдите в ваш акаунт или введите другой номер телефона");
        }
        if (!PhoneEmailValidator.checkTelNumber(phone)) {
            errormessagelist.add("Проверьте корректность номера телефона");
        }
        if ("".equals(address)) {
            errormessagelist.add("Не указан адрес");
        }
        if (!errormessagelist.isEmpty()) {
            model.addAttribute("cart", cart);
            model.addAttribute("errormessagelist", errormessagelist);
            return "order_fastmessage";
        }

        String[] fastUser = userService.fastCreateUser(phone);
        User user = userService.findByPhone(fastUser [0]);

        Order order = new Order(user, cart, address, phone);
        orderService.save(order);
        model.addAttribute("login",fastUser[0]);
        model.addAttribute("password",fastUser[1]);
        model.addAttribute("order_id_str", String.format("%04d", order.getId()));
        return "order_fastconfirm";
    }


}

