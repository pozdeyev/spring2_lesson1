package com.geekbrains.decembermarket.utils;

import com.geekbrains.decembermarket.entites.Comment;
import com.geekbrains.decembermarket.repositories.specifications.CommentSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;


@Getter
public class CommentFilter {
    private Specification<Comment> spec;

    public CommentFilter(Long product_id) {
        this.spec = Specification.where(null);
        spec = spec.and(CommentSpecifications.productIdEquals(product_id));
    }

}
