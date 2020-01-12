package com.geekbrains.decembermarket.repositories.specifications;

import com.geekbrains.decembermarket.entites.Comment;
import org.springframework.data.jpa.domain.Specification;


public class CommentSpecifications {
    public static Specification<Comment> productIdEquals(long product_id) {
        return (Specification<Comment>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("product").get("id"), product_id);
    }

}
