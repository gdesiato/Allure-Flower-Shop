package com.giuseppe.allureshop.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Data
@Entity
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    @OneToOne
    private Customer customer;

    public void addItem(CartItem item) {
    }

    public void removeItem(CartItem item) {
    }

    public void clearItems() {
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        if (this.items != null) {
            for (CartItem item : this.items) {
                totalPrice += item.getTotalPrice();
            }
        }
        return totalPrice;
    }
}
