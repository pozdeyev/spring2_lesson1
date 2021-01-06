package com.geekbrains.decembermarket.services;

import com.geekbrains.decembermarket.entites.*;
import com.geekbrains.decembermarket.repositories.RoleRepository;
import com.geekbrains.decembermarket.repositories.UserRepository;
import com.geekbrains.decembermarket.utils.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder; //объект пароль/дешифровка

    private ProductService productService;
    private CommentService commentService;


    public UserServiceImpl(ProductService productService, CommentService commentService) {
        this.productService = productService;
        this.commentService = commentService;
    }


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public User findByPhone(String phone) {
        return userRepository.findOneByPhone(phone);
    }
    public User findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByPhone(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public boolean isUserExist(String phone) {
        return userRepository.existsByPhone(phone);
    }


    @Override
    @Transactional
    public User save(SysUser systemUser) {
        User user = new User();
        if (findByPhone(systemUser.getPhone()) != null) {
            throw new RuntimeException("User with phone " + systemUser.getPhone() + " is already exist");
        }
        user.setPhone(systemUser.getPhone());
        if (systemUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        }

        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findOneByName("ROLE_CUSTOMER")));

        //заполняем токен для подтверждения почты
        user.setEmail_token(RandomStringUtils.random(6, "abcdefghijklmnopqrstuvwxyz1234567890"));

        //заполняем поле подтверждения почты
        user.setEmail_approve(false);

        return userRepository.save(user);
    }

    public User save_user (User user) {
        return userRepository.save(user);
    }

    public User findUserByToken (String token) {
        return userRepository.findByToken(token);
    }

    public String[] fastCreateUser(String phone) {
        int length = 6;
        boolean useLetters = true;
        boolean useNumbers = true;

        String username = phone;
        String password = RandomStringUtils.random(length, useLetters, useNumbers);

        String encodedPass = passwordEncoder.encode(password);
        User fastUser = new User(username, encodedPass);
        userRepository.save(fastUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new String[]{username, password};
    }

    public boolean isProductCustomer (User user, Product product) {
        Long userID = user.getId(); //id текущего пользователя
        List<Product> products = productService.ProductListUserPurchasedByUserID (userID);
        if (!products.isEmpty() & products.contains(product)) {
            return true;
        } else return false;
    }

    public boolean isProductCommentator (User user, Product product) {
        Long userID = user.getId(); //id текущего пользователя
        Long productID=product.getId(); //id продукта
        List<Comment> comments = commentService.CommentedListByUserAndProductID(userID, productID); //формируем список

        // Если не пустой возвращаем
        if (!comments.isEmpty()) {
            return true;
        } else return false;
    }
}