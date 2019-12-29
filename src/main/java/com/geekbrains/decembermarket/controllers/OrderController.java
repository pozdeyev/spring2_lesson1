package com.geekbrains.decembermarket.controllers;
import com.geekbrains.decembermarket.beans.Cart;
import com.geekbrains.decembermarket.entites.Order;
import com.geekbrains.decembermarket.entites.User;
import com.geekbrains.decembermarket.services.OrderService;
import com.geekbrains.decembermarket.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private UserService userService;
    private OrderService orderService;
    private Cart cart;

    public OrderController(UserService userService, OrderService orderService, Cart cart) {
        this.userService = userService;
        this.orderService = orderService;
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
        Order order = new Order(user, cart, address, phone);
        order = orderService.save(order);
        model.addAttribute("order_id_str", String.format("%04d", order.getId()));
        return "order_confirm";
    }

}
