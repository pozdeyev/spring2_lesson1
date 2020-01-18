package com.geekbrains.decembermarket.repositories;

import com.geekbrains.decembermarket.entites.Comment;
import com.geekbrains.decembermarket.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    @Query("SELECT c FROM Comment c WHERE (c.user.id=:userid) and (c.product.id=:productid)")
    List <Comment> CommentedListByUserProductID(Long userid, Long productid);
}