package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.Order;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.OrderService;
import com.giuseppe.allureshop.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.logging.Logger;

@Controller
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    @PostMapping("/process-order")
    public String processOrder(@ModelAttribute("order") Order order,
                               BindingResult bindingResult,
                               Model model,
                               Principal principal,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "orderForm";
        }

        User user = userService.findByUsername(principal.getName());
        order.setUser(user);

        Cart cart = cartService.getShoppingCartForUser(user.getUsername());
        double totalCost = cart.getTotalPrice();
        LOGGER.info("Total cost: " + totalCost);

        order.setTotalCost(totalCost);

        redirectAttributes.addFlashAttribute("totalCost", totalCost);
        LOGGER.info("Stored flash attribute: " + redirectAttributes.getFlashAttributes());

        orderService.save(order);

        redirectAttributes.addFlashAttribute("totalCost", totalCost);

        model.addAttribute("items", cart.getItems());

        return "redirect:/order-confirmation";
    }

    @GetMapping("/order-confirmation")
    public String orderConfirmation(Model model, RedirectAttributes redirectAttributes) {
        LOGGER.info("Retrieved flash attribute: " + redirectAttributes.getFlashAttributes());
        if (redirectAttributes.getFlashAttributes().containsKey("totalCost")) {
            model.addAttribute("totalCost", redirectAttributes.getFlashAttributes().get("totalCost"));
        }
        return "order-confirmation";
    }

}
