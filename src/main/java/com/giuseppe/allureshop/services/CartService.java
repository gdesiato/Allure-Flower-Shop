package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.exceptions.CartNotFoundException;
import com.giuseppe.allureshop.exceptions.FlowerNotFoundException;
import com.giuseppe.allureshop.exceptions.UserNotFoundException;
import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.CartItem;
import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.repositories.CartItemRepository;
import com.giuseppe.allureshop.repositories.CartRepository;
import com.giuseppe.allureshop.repositories.FlowerRepository;
import com.giuseppe.allureshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FlowerRepository flowerRepository;

    @Transactional
    public Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setUsername(user.getUsername());
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart getShoppingCartForUser(String username) {
        return cartRepository.findByUsername(username);
    }

    // Add flower to the cart
//    @Transactional
//    public void addToCart(Cart cartId, Optional<Flower> flower, int quantity) {
//        CartItem item = new CartItem(flower, quantity);
//        cartId.addItem(item);
//        cartRepository.save(cartId);
//    }

    public Cart addToCart(Long userId, Long flowerId, Integer quantity) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new FlowerNotFoundException("Flower not found with id: " + flowerId));

        CartItem existingCartItem = user.getCartItems().stream()
                .filter(cartItem -> cartItem.getFlower().getId().equals(flowerId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // Update the quantity of the existing cart item
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            // Create a new cart item
            CartItem newCartItem = new CartItem();
            newCartItem.setFlower(flower);
            newCartItem.setQuantity(quantity);
            newCartItem.setUser(user);

            // Save the new cart item
            cartItemRepository.save(newCartItem);

            // Add the new cart item to the user's cart
            user.getCartItems().add(newCartItem);
        }

        // Save the updated user
        userRepository.save(user);

        // Return the user's cart
        return user.getCart();
    }


    // remove product from the cart
    @Transactional
    public void removeItemFromCart(Cart cart, CartItem item) {
        cart.removeItem(item);
        cartRepository.save(cart);
    }

    // empty the cart
    @Transactional
    public void clearCart(Cart cart) {
        cart.clearItems();
        cartRepository.save(cart);
    }

    @Transactional
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id " + cartId + " not found"));
    }

    @Transactional
    public Cart createCart(Long customer) {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public Cart findCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}