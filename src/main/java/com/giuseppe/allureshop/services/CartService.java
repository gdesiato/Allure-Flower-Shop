package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.exceptions.CartNotFoundException;
import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.CartItem;
import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public Cart getShoppingCartForUser(String username) {
        return cartRepository.findByUsername(username);
    }

    // Add flower to the cart
    public void addToCart(Cart cartId, Optional<Flower> flower, int quantity) {
        CartItem item = new CartItem(flower, quantity);
        cartId.addItem(item);
        cartRepository.save(cartId);
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

    public Cart createCart(Long customer) {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }
}