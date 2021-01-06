package com.geekbrains.decembermarket.repositories;

import com.geekbrains.decembermarket.entites.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByPhone(String phone);
    User findOneByEmail (String email);
    boolean existsByPhone(String phone);

    @Query("SELECT p FROM User p WHERE p.email_token=:token")
    User findByToken(String token);
}