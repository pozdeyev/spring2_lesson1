package com.geekbrains.decembermarket.repositories.specifications;
import com.geekbrains.decembermarket.entites.Order;

import org.springframework.data.jpa.domain.Specification;


public class OrderSpecifications {
    public static Specification <Order> userIdEquals(Long user_id) {
        return (Specification <Order>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), user_id);
    }
}
