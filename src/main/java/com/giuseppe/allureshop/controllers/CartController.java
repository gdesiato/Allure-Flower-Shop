package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.*;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.FlowerService;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlowerService flowerService;


    @GetMapping
    @Transactional
    public String getCart(@AuthenticationPrincipal User user, Model model) {
        Cart cart = cartService.getShoppingCartForUser(user.getUsername());
        model.addAttribute("cart", cart);
        if (cart == null) {
            return "no-cart";
        }
        return "cart";
    }

    @PostMapping("/new/{customerId}")
    @Transactional
    public String createNewCart(@PathVariable Long customerId, Model model) {
        Cart cart = cartService.createCart(customerId);
        model.addAttribute("cart", cart);
        return "new-cart";
    }

    @GetMapping("/{cartId}")
    @Transactional
    public String getCartById(@PathVariable Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "error";
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    // change for user

    @PostMapping("/{userId}")
    @Transactional
    public String addToCart(@PathVariable Long userId, @RequestParam Long flowerId, @RequestParam int quantity, Model model) {
        Optional<User> user = userService.getUserById(userId);
        if (!user.isPresent()) {
            return "error";
        }
        Cart cart = user.get().getCart();
        Optional<Flower> flower = flowerService.getFlowerById(flowerId);
        if (flower == null) {
            return "error";
        }
        cartService.addToCart(cart, flower, quantity);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/{cartId}/items")
    @Transactional
    public String addItemToCart(@PathVariable Long cartId, @RequestBody CartItem item, @RequestParam Optional<Flower> flower, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "error";
        }
        cartService.addToCart(cart, flower, item.getQuantity());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @Transactional
    public String removeItemFromCart(@PathVariable Long cartId, @PathVariable CartItem itemId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "error";
        }
        cartService.removeItemFromCart(cart, itemId);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/{cartId}/total")
    @Transactional
    public String getTotalPrice(@PathVariable Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "error";
        }
        double totalPrice = cart.getTotalPrice();
        model.addAttribute("totalPrice", totalPrice);
        return "totalPrice";
    }
}