package com.geekbrains.decembermarket.repositories.specifications;

import com.geekbrains.decembermarket.entites.Order;
import com.geekbrains.decembermarket.entites.OrderItem;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class OrderItemSpecification {

    public static Specification <OrderItem> orderIdEquals(long order_id) {
        return (Specification<OrderItem>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("order").get("id"), order_id);
    }

}
