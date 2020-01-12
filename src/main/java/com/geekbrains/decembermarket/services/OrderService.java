package com.geekbrains.decembermarket.services;
import com.geekbrains.decembermarket.entites.Order;

import com.geekbrains.decembermarket.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;


    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


     public List<Order> findAllList(Specification<Order> spec) {
       return orderRepository.findAll(spec);
    }


    public List<Order> getAll() {
        return orderRepository.findAll();
    }


    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }



    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
