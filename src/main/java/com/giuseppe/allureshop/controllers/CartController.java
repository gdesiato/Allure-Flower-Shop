package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.*;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.CustomerService;
import com.giuseppe.allureshop.services.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FlowerService flowerService;


    @GetMapping
    public String getCart(@AuthenticationPrincipal User user, Model model) {
        Cart cart = cartService.getShoppingCartForUser(user.getUsername());
        model.addAttribute("cart", cart);
        if (cart == null) {
            return "no-cart";
        }
        return "cart";
    }

//    @GetMapping("/new/{customerId}")
//    public String createNewCart(@PathVariable Long customerId, Model model) {
//        Cart cart = cartService.createCart(customerId);
//        model.addAttribute("cart", cart);
//        return "new-cart";
//    }

    @PostMapping("/new/{customerId}")
    public String createNewCart(@PathVariable Long customerId, Model model) {
        Cart cart = cartService.createCart(customerId);
        model.addAttribute("cart", cart);
        return "new-cart";
    }

    @GetMapping("/{cartId}")
    public String getCartById(@PathVariable Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "error";
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/{customerId}")
    public String addToCart(@PathVariable Long customerId, @RequestParam Long flowerId, @RequestParam int quantity, Model model) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (!customer.isPresent()) {
            return "error";
        }
        Cart cart = customer.get().getCart();
        Optional<Flower> flower = flowerService.getFlowerById(flowerId);
        if (flower == null) {
            return "error";
        }
        cartService.addToCart(cart, flower, quantity);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/{cartId}/items")
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