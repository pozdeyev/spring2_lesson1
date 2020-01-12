package com.geekbrains.decembermarket.repositories;


import com.geekbrains.decembermarket.entites.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByPhone(String phone);
    User findOneByEmail (String email);
    boolean existsByPhone(String phone);
}