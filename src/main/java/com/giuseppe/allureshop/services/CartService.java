package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.exceptions.CartNotFoundException;
import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.CartItem;
import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    // Add flower to the cart
    public void addToCart(Cart cart, Flower flower, int quantity) {
        CartItem item = new CartItem(flower, quantity);
        cart.addItem(item);
        cartRepository.save(cart);
    }

    // remove product from the cart
    public void removeItemFromCart(Cart cart, CartItem item) {
        cart.removeItem(item);
        cartRepository.save(cart);
    }

    // empty the cart
    public void clearCart(Cart cart) {
        cart.clearItems();
        cartRepository.save(cart);
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id " + cartId + " not found"));
    }

}