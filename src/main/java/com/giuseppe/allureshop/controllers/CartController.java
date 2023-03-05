package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.CartItem;
import com.giuseppe.allureshop.models.Customer;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{customerId}")
    public String createCart(@PathVariable Long customerId, Model model) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return "error"; // return error template
        }
        Cart cart = cartService.createCart(customer);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/{cartId}/items")
    public String addItemToCart(@PathVariable Long cartId, @RequestBody CartItem item, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "error";
        }
        CartItem addedItem = cartService.addToCart(cart, item);
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

    @GetMapping("/{cartId}")
    public String getCartById(@PathVariable Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "error"; // return error template
        }
        model.addAttribute("cart", cart); // pass cart object to the template
        return "cart"; // return cart template
    }

    @GetMapping("/{cartId}/total")
    public String getTotalPrice(@PathVariable Long cartId, Model model) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return "error"; // return error template
        }
        double totalPrice = cart.getTotalPrice();
        model.addAttribute("totalPrice", totalPrice); // pass total price to the template
        return "totalPrice"; // return total price template
    }
}