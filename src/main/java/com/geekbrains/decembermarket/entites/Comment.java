package com.geekbrains.decembermarket.entites;
import com.geekbrains.decembermarket.beans.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment")
    private String comment;

    @Column(name = "mark")
    private int mark;


    public Comment(Product product, User user, String comment,  int mark) {
        this.user = user;
        this.product = product;
        this.comment = comment;
        this.mark=mark;

    }
}
