package com.geekbrains.decembermarket.controllers;

import com.geekbrains.decembermarket.entites.Comment;

import com.geekbrains.decembermarket.entites.Order;
import com.geekbrains.decembermarket.entites.Product;
import com.geekbrains.decembermarket.entites.User;
import com.geekbrains.decembermarket.services.CommentService;

import com.geekbrains.decembermarket.services.OrderService;
import com.geekbrains.decembermarket.services.UserService;
import com.geekbrains.decembermarket.services.ProductService;
import com.geekbrains.decembermarket.utils.CommentFilter;

import com.geekbrains.decembermarket.utils.OrderFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ShowProductController {
    private UserService userService;
    private ProductService productService;
    private CommentService commentService;
    private OrderService orderService;


    public ShowProductController(UserService userService, ProductService productService, CommentService commentService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.commentService = commentService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public String editProductForm(Model model, @PathVariable Long id,  Principal principal) {
        Product product = productService.findById(id);

        if (principal != null) {
            User user = userService.findByPhone(principal.getName());
            System.out.println("inIF**********************");
            //Проверяем покупал ли текущий пользователь этот товар
            if (userService.isProductCustomer(user, product)) {
               System.out.println("isCustomer******************");
                model.addAttribute("customer", "isCustomer");

            } else {
                System.out.println("isNotCustomer******************");
                model.addAttribute("customer", "isNotCustomer");
            }

         //   Long userID = user.getId(); //id текущего пользователя
       //     OrderFilter orderFilter = new OrderFilter(userID);
      ///      System.out.println(userID);
       //     System.out.println(product.getId());
       //     List<Order> order = orderService.findAllList(orderFilter.getSpec());
            //  System.out.println(order);
       //     Iterator order_iterator = order.iterator();

       //     while (order_iterator.hasNext()) {
        //        Order element = (Order) order_iterator.next();
       //         System.out.println((element.getItems().contains(product)));
        //        if (element.getItems().contains(product)) { //если находим продукт выходим из итератор
        //        }
        //    }

        }

        CommentFilter commentFilter = new CommentFilter(id);
        List<Comment> comment = commentService.findAllList(commentFilter.getSpec()); //фильтруем по продукт id

        model.addAttribute("comment", comment);
        model.addAttribute("product", product);




        return "product_page";
    }

}
