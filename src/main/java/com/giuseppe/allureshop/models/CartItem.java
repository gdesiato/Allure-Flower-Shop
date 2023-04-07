package com.giuseppe.allureshop.models;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Flower flower;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public CartItem(Optional<Flower> flower, int quantity) {
    }

    public double getTotalPrice() {
        return quantity * flower.getPrice();
    }
}
