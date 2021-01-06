package com.geekbrains.decembermarket.repositories;
import com.geekbrains.decembermarket.entites.Product;
import com.geekbrains.decembermarket.entites.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

//Список продуктов, который покупал пользователь по его ID
      @Query("SELECT p FROM Product p WHERE p.id in " +
              "(SELECT oi.product.id FROM OrderItem oi WHERE oi.order.id in " +
              "(SELECT o.id FROM  Order o WHERE (o.user.id=:id)))")
      List<Product> ProductListByUserID(Long id);
      List<ProductDto> findAllBy();
}