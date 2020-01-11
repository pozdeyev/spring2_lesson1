package com.geekbrains.decembermarket.controllers;



import com.geekbrains.decembermarket.entites.User;
import com.geekbrains.decembermarket.services.UserServiceImpl;
import com.geekbrains.decembermarket.utils.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/register")
public class RegController {
    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

//убрать пробелы
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("")
    public String showMyLoginPage(Model model) {
        model.addAttribute("systemUser", new SysUser());
        return "registration_form";
    }

    @PostMapping("/process")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SysUser systemUser, BindingResult bindingResult, Model model) {
        String username = systemUser.getPhone();
        String email=systemUser.getEmail();
        if (bindingResult.hasErrors()) {
            return "registration_form";
        }
        User existingPhone = userService.findByPhone(username);
        User existingEmail= userService.findByEmail(email);

        if (existingPhone != null) {

            model.addAttribute("systemUser", systemUser);
            model.addAttribute("registrationError", "User with current phone is exist");
            return "registration_form";
        }

        if (existingEmail != null) {

            model.addAttribute("systemUser", systemUser);
            model.addAttribute("registrationError", "User with current email is exist");
            return "registration_form";
        }


        userService.save(systemUser);
        return "registration_confirm";
    }

}
