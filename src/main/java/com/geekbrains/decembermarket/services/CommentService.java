package com.geekbrains.decembermarket.services;

import com.geekbrains.decembermarket.entites.Comment;
import com.geekbrains.decembermarket.entites.Product;
import com.geekbrains.decembermarket.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAllList(Specification<Comment> spec) {
        return commentRepository.findAll(spec);
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> CommentedListByUserAndProductID(Long user_id, Long product_id) {
        return commentRepository.CommentedListByUserProductID(user_id,product_id);
    }
}