package com.geekbrains.decembermarket.utils;

import com.geekbrains.decembermarket.utils.validation.FieldMatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
public class SysUser {

    @NotNull(message = "Phone is required")
    @Size(min = 8, message = "Phone is too short")
    private String phone;

    @NotNull(message = "Password is required")
    @Size(min = 4, message = "Password is too short")
    private String password;

    @NotNull(message = "Password is required")
    @Size(min = 4, message = "Password is too short")
    private String matchingPassword;

    @NotNull(message = "Firstname is required")
    @Size(min = 1, message = "Firstname is too short")
    private String firstName;

    @NotNull(message = "Lastname is required")
    @Size(min = 1, message = "Lastname is too short")
    private String lastName;

    @NotNull(message = "Email is required")
    @Size(min = 4, message = "Email is too short")
    @Email
    private String email;
}
