package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.Order;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.OrderService;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/process-order")
    public String processOrder(@ModelAttribute("order") Order order,
                               BindingResult bindingResult,
                               Model model,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {  // add RedirectAttributes as a parameter

        if (bindingResult.hasErrors()) {
            return "orderForm";
        }

        User user = userService.findByUsername(principal.getName());
        order.setUser(user);

        // Get the total cost from the user's cart
        Cart cart = cartService.getShoppingCartForUser(user.getUsername());
        double totalCost = cart.getTotalPrice();
        System.out.println("Total cost: " + totalCost); // Log total cost


        // Save the total cost in the order (if you have such a field in the Order class)
        order.setTotalCost(totalCost);

        redirectAttributes.addFlashAttribute("totalCost", totalCost);
        System.out.println("Stored flash attribute: " + redirectAttributes.getFlashAttributes());


        // Save the order
        orderService.save(order);

        // Add total cost to the RedirectAttributes
        redirectAttributes.addFlashAttribute("totalCost", totalCost);

        // If you need to display cart items on the order confirmation page
        model.addAttribute("items", cart.getItems());

        return "redirect:/order-confirmation";
    }

    @GetMapping("/order-confirmation")
    public String orderConfirmation(Model model) {
        System.out.println("Retrieved flash attribute: " + model.asMap());
        return "order-confirmation";
    }

}
