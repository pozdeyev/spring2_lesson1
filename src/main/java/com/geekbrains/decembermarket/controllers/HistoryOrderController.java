package com.geekbrains.decembermarket.controllers;

import com.geekbrains.decembermarket.entites.*;

import com.geekbrains.decembermarket.services.OrderService;
import com.geekbrains.decembermarket.services.UserService;

import com.geekbrains.decembermarket.utils.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/history")
public class HistoryOrderController {
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public HistoryOrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/info")
    public String showOrderInfo(Model model, Principal principal) {

        //ищем пользователя, его имя, телефон
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("def_phone", user.getPhone());
        model.addAttribute("def_name", user.getFirstName());


        Long id = user.getId(); //id пользователя

        //Заказы по userID
        OrderFilter orderFilter = new OrderFilter(id);
        List<Order> order = orderService.findAllList(orderFilter.getSpec());
        model.addAttribute("order", order);
        return "history_order";
    }
}
