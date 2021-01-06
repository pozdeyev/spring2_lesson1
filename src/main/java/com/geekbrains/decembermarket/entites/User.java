package com.geekbrains.decembermarket.entites;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "email_token")
    private String email_token;

    @Column(name = "email_approve")
    private Boolean email_approve;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(String phone, String password, String firstName, String lastName, String email,
                String email_token, Boolean email_approve) {
        this.phone = phone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.email_token = email_token;
        this.email_approve = email_approve;
    }

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
        this.firstName = "Аноним";
        this.lastName = "Фамилия";
        this.email = "";
    }

}
