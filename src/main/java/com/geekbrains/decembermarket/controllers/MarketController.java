package com.geekbrains.decembermarket.controllers;

import com.geekbrains.decembermarket.entites.Category;
import com.geekbrains.decembermarket.entites.Order;
import com.geekbrains.decembermarket.entites.Product;
import com.geekbrains.decembermarket.entites.User;
import com.geekbrains.decembermarket.services.CategoryService;
import com.geekbrains.decembermarket.services.OrderService;
import com.geekbrains.decembermarket.services.ProductService;
import com.geekbrains.decembermarket.services.UserService;
import com.geekbrains.decembermarket.utils.Cart;
import com.geekbrains.decembermarket.utils.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MarketController {
    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;
    private OrderService orderService;
    private Cart cart;
    private Order order;

    public MarketController(ProductService productService, CategoryService categoryService, UserService userService, OrderService orderService, Cart cart) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.orderService = orderService;
        this.cart = cart;
        this.order = order;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        int pageIndex = 0;
        String sort = "id"; //сортировка по умолчанию


        if (params.containsKey("p")) {
            pageIndex = Integer.parseInt(params.get("p")) - 1;
        }

        if (params.containsKey("sort_by") && !params.get("sort_by").isEmpty()) {
            sort = params.get("sort_by");
        }

        Pageable pageRequest = PageRequest.of(pageIndex, 5, Sort.Direction.ASC, sort);
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> page = productService.findAll(productFilter.getSpec(), pageRequest);
        List<Category> categories = categoryService.getAll();

        model.addAttribute("filtersDef", productFilter.getFilterDefinition());
        model.addAttribute("categories", categories);
        model.addAttribute("page", page);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(Model model, @PathVariable Long id) {
        Product product = productService.findById(id);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "edit_product";
    }

    @PostMapping("/edit")
    public String saveProduct(@ModelAttribute(name = "product") Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/cart/add/{id}")
    public void addProductToCart(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.add(productService.findById(id));
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("cart/delete/{id}")
    public String deleteProductToCart(Model model, @PathVariable("id") Long id) {
        cart.removeById(id);
        return "redirect:/cart/";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart_page";
    }

    @GetMapping("/orders")
    public String showOrder(Model model, Principal principal) {
        User user = userService.findByPhone(principal.getName());
        Order order = new Order(user, cart);
        model.addAttribute("order", order);
      //  orderService.save(order);
        return "order_message";
    }


    @GetMapping("/order_create")
    public String createOrder(Principal principal,
                              @RequestParam(name = "address") String address,
                              @RequestParam(name = "phone") String phone) {
        User user = userService.findByPhone(principal.getName());
         Order order = new Order(user, cart);
        order.setAddress(address); //Адрес
        if (phone.equals("")) {
            order.setContact_phone(user.getPhone()); //записываем телефон текущего пользователя, если ничего не вводили
            // тайм лиф не передает строку, если в ней не печатать. Пока не понял как это решить по другому
        } else order.setContact_phone(phone); //записываем телефон, который ввел человек
        orderService.save(order);
        cart.clear();
        return "redirect:/";
    }
}


//  public String createOrder()
//  {
//      System.out.println("_________________________________________________phone");
//     System.out.println("_________________________________________________address");
//    User user = userService.findByPhone(principal.getName());

//   Order order = new Order(user, cart);
//  order.setAddress(address); //Адрес
//     order.setContact_phone(phone); //Телефон

//orderService.save(order);

//      return "redirect:/";

//   }
