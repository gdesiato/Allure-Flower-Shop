package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.Order;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.OrderService;
import com.giuseppe.allureshop.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.sql.SQLOutput;

@Controller
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/process-order")
    public String processOrder(@ModelAttribute("order") Order order,
                               BindingResult bindingResult,
                               Model model,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {

        System.out.println("PROCESS ORDER ENDPOINT REACHED");

        try {
            LOGGER.info("Entered processOrder method.");
            if (bindingResult.hasErrors()) {
                return "orderForm";
            }

            // Get the current user
            User user = userService.findByUsername(principal.getName());

            // Set the user for the order
            order.setUser(user);

            // Get the user's cart
            Cart userCart = cartService.getShoppingCartForUser(user.getUsername());

            // Calculate total cost and set it to order
            double totalCost = userCart.getTotalPrice();
            order.setTotalCost(totalCost);

            // Process the order and clear the cart
            Order savedOrder = orderService.processOrderAndClearCart(order, userCart);

            // Add the saved order and the user as flash attributes
            redirectAttributes.addFlashAttribute("order", savedOrder);
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("totalCost", totalCost);

            // Add items to the model
            model.addAttribute("items", userCart.getItems());

            LOGGER.info("Total cost: " + totalCost);
            LOGGER.info("Stored flash attribute: " + redirectAttributes.getFlashAttributes());

        } catch(Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            LOGGER.info("Exception in processOrder: " + e.getMessage() + "\nStackTrace: " + stringWriter.toString());
        }
        return "redirect:/order-confirmation";
    }


    @GetMapping("/order-confirmation")
    public String orderConfirmation(Model model, Principal principal) {
        try {
            LOGGER.info("Entered orderConfirmation method.");
            User user = userService.findByUsername(principal.getName());
            Order order = orderService.findOrderByUser(user);

            if (order != null) {
                LOGGER.info("Order found: " + order.getId() + ", Total Cost: " + order.getTotalCost());
                model.addAttribute("totalCost", order.getTotalCost());
            } else {
                LOGGER.info("No order found for user: " + user.getUsername());
                return "redirect:/user/dashboard";
            }

        } catch(Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            LOGGER.info("Exception in orderConfirmation: " + e.getMessage() + "\nStackTrace: " + stringWriter.toString());
        }

        return "order-confirmation";
    }


}
