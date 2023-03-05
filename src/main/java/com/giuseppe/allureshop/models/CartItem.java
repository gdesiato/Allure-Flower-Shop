package com.giuseppe.allureshop.models;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Flower flower;

    private int quantity;

    public CartItem(Optional<Flower> flower, int quantity) {
    }

    public double getTotalPrice() {
        return quantity * flower.getPrice();
    }
}
