package com.geekbrains.decembermarket.controllers;

import com.geekbrains.decembermarket.entites.*;

import com.geekbrains.decembermarket.services.CommentService;

import com.geekbrains.decembermarket.services.OrderService;
import com.geekbrains.decembermarket.services.UserService;
import com.geekbrains.decembermarket.services.ProductService;
import com.geekbrains.decembermarket.utils.CommentFilter;

import com.geekbrains.decembermarket.utils.HistoryVisitedUtils;
import com.geekbrains.decembermarket.utils.OrderFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ShowProductController {
    private UserService userService;
    private ProductService productService;
    private CommentService commentService;


    public ShowProductController(UserService userService, ProductService productService, CommentService commentService) {
        this.userService = userService;
        this.productService = productService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String productForm(Model model, @PathVariable Long id,
                              @CookieValue(value = "lastProducts", required = false) String lastProducts,
                              HttpServletResponse response, Principal principal) {
        Product product = productService.findById(id);

        if (principal != null) {

            User user = userService.findByPhone(principal.getName());

            if ((userService.isProductCustomer(user, product)) && (!userService.isProductCommentator(user, product))) {
                model.addAttribute("customer", "isCustomer"); //можно оставить комментарий (не комментировал и покупал товар)

            }
            if ((userService.isProductCustomer(user, product)) && (userService.isProductCommentator(user, product))) {
                model.addAttribute("customer", "isAlreadyCommentCustomer"); //нельзя оставлять комментарии (уже комментировал)

            }
            if (!userService.isProductCustomer(user, product)) {
                model.addAttribute("customer", "isNotCustomer"); //нельзя оставлять комментарии (не покупал)

            }
        }

        if (lastProducts == null) lastProducts = String.valueOf(product.getId());
        else lastProducts = lastProducts + "q" + product.getId();
        LinkedList<String> lastProductsIndexesList = HistoryVisitedUtils.cutVisitedProductsHistory(lastProducts, 5);
        response.addCookie(new Cookie("lastProducts", HistoryVisitedUtils.listToString(lastProductsIndexesList)));
        LinkedList<Product> productsList = new LinkedList<>();
        for (String s : lastProductsIndexesList) productsList.add(productService.findById(Long.parseLong(s)));


        CommentFilter commentFilter = new CommentFilter(id);
        List<Comment> comment = commentService.findAllList(commentFilter.getSpec()); //фильтруем по продукт id
        model.addAttribute("comment", comment);
        model.addAttribute("product", product);
        model.addAttribute("productsList", productsList);
        return "product_page";
    }

    @PostMapping("/comment")
    public String comment( Model model,  @CookieValue(value = "lastProducts", required = false) String lastProducts,
                           HttpServletResponse response,
                           @RequestParam String usercomment, @RequestParam String mark,
                           @RequestParam Long productid,  Principal principal) {

        User user = userService.findByPhone(principal.getName());
        Product product = productService.findById(productid);

        Comment newcomment = new Comment(product, user, usercomment, Integer.parseInt(mark.trim()));
        newcomment = commentService.save(newcomment);

        CommentFilter commentFilter = new CommentFilter(productid);
        List<Comment> comment = commentService.findAllList(commentFilter.getSpec());

        if (lastProducts == null) lastProducts = String.valueOf(product.getId());
        else lastProducts = lastProducts + "q" + product.getId();
        LinkedList<String> lastProductsIndexesList = HistoryVisitedUtils.cutVisitedProductsHistory(lastProducts, 5);
        response.addCookie(new Cookie("lastProducts", HistoryVisitedUtils.listToString(lastProductsIndexesList)));
        LinkedList<Product> productsList = new LinkedList<>();
        for (String s : lastProductsIndexesList) productsList.add(productService.findById(Long.parseLong(s)));


        model.addAttribute("product", product);
        model.addAttribute("customer", "isAlreadyCommentCustomer");
        model.addAttribute("comment", comment);
        model.addAttribute("productsList", productsList);

        return "product_page";
    }


}
