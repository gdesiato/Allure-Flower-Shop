package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.*;
import com.giuseppe.allureshop.repositories.CartItemRepository;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.FlowerService;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlowerService flowerService;

    @Autowired
    private CartItemRepository cartItemRepository;


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

    @GetMapping("/{userId}")
    public String viewCart(@PathVariable("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);

        if (user == null) {
            return "error";
        }

        Cart cart = user.getCart();

        model.addAttribute("cart", cart);

        return "cart";
    }


    @PostMapping("/{userId}")
    @Transactional
    public String addToCart(@PathVariable("userId") Long userId,
                            @RequestParam("flowerName") String flowerName,
                            @RequestParam("quantity") Integer quantity,
                            Model model) {

        Cart cart = cartService.addToCart(userId, flowerName, quantity);

        if (cart == null) {
            return "error";
        }

        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/{userId}/items")
    @Transactional
    public String addItemToCart(@PathVariable("userId") Long userId,
                                @RequestParam("flowerName") String flowerName,
                                @RequestParam("quantity") Integer quantity,
                                Model model) {

        Cart cart = cartService.addToCart(userId, flowerName, quantity);

        if (cart == null) {
            return "error";
        }

        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/remove/{itemId}")
    @Transactional
    public String removeItemFromCart(@PathVariable Long itemId, @AuthenticationPrincipal User user, Model model) {
        Cart cart = cartService.getShoppingCartForUser(user.getUsername());
        if (cart == null) {
            return "error";
        }

        CartItem cartItem = cartItemRepository.findById(itemId).orElse(null);
        if (cartItem == null) {
            return "error";
        }

        cartService.removeItemFromCart(cart, cartItem);
        model.addAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    @Transactional
    public String clearCart(@AuthenticationPrincipal User user, Model model) {
        Cart cart = cartService.getShoppingCartForUser(user.getUsername());
        if (cart == null) {
            return "error";
        }

        cartService.clearCart(cart);
        model.addAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/{cartId}/total")
    @Transactional
    public String getTotalPrice(@PathVariable Long cartId, Model model) {
        double totalCost = cartService.getCartTotal(cartId);
        model.addAttribute("totalCost", totalCost);
        return "totalPrice";
    }

}