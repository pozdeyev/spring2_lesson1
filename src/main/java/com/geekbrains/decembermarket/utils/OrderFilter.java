package com.geekbrains.decembermarket.utils;

import com.geekbrains.decembermarket.entites.Order;

import com.geekbrains.decembermarket.repositories.specifications.OrderSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;



@Getter
public class OrderFilter {
    private Specification<Order> spec;

    public OrderFilter(Long user_id) {
        this.spec = Specification.where(null);
        spec = spec.and(OrderSpecifications.userIdEquals(user_id));
    }
}
