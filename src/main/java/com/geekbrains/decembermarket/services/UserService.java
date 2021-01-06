package com.geekbrains.decembermarket.services;

import com.geekbrains.decembermarket.entites.Product;
import com.geekbrains.decembermarket.entites.User;
import com.geekbrains.decembermarket.utils.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByPhone(String phone);
    User findByEmail (String email);
    User findUserByToken (String token);
    User save(SysUser systemUser);
    User save_user (User user);
    String[] fastCreateUser(String phone);
    boolean isProductCustomer(User user, Product product);
    boolean isProductCommentator (User user, Product product);
}
