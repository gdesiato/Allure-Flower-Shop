package com.giuseppe.allureshop.models;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flower_id", nullable = false)
    private Flower flower;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Add a reference to the User class
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CartItem(Optional<Flower> flower, int quantity) {
    }

    public double getTotalPrice() {
        return quantity * flower.getPrice();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
