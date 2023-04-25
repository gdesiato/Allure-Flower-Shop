package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.CartItem;
import com.giuseppe.allureshop.models.Order;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.repositories.RoleRepository;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.OrderService;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController implements ErrorController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;


    @GetMapping("/list")
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/user/list";
    }

    @GetMapping
    public String getUser(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        return "user-view";
    }

    @GetMapping("/update/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/dashboard")
    public String showUserDashboard(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);

            if (user != null) {
                model.addAttribute("user", user);

                // Check if the user has an admin role
                boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

                if (isAdmin) {
                    // Redirect to the admin dashboard if the user is an admin
                    return "redirect:/admin/dashboard";
                } else {
                    // Handle the user dashboard
                    Cart userCart = cartService.findCartByUser(user);

                    if (userCart == null) {
                        userCart = new Cart();
                        userCart.setUser(user);
                        //userCart.setUsername(user.getUsername());
                        userCart = cartService.saveCart(userCart);
                    }

                    List<CartItem> items = userCart.getItems();

                    model.addAttribute("cart", userCart);
                    model.addAttribute("items", items);
                }
            }
        }
        return "user-dashboard-frag";
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);

            if (user != null) {
                Cart userCart = cartService.findCartByUser(user);

                if (userCart == null) {
                    return "redirect:/user/dashboard";
                }

                List<CartItem> items = userCart.getItems();
                model.addAttribute("cart", userCart);
                model.addAttribute("items", items);
                model.addAttribute("user", user);
                model.addAttribute("order", new Order());

                return "checkout";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/process-order")
    public String processOrder(@ModelAttribute("order") Order order, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        // Get the current user
        User user = userService.findByUsername(principal.getName());

        // Set the user for the order
        order.setUser(user);

        // Get the user's cart
        Cart userCart = cartService.findCartByUser(user);

        // Add the cart items to the order
        List<CartItem> items = userCart.getItems();
        order.setItems(items);

        // Save the order
        Order savedOrder = orderService.saveOrder(order);

        // Clear the user's cart
        userCart.getItems().clear();
        cartService.saveCart(userCart);

        // Add the saved order and the user as flash attributes
        redirectAttributes.addFlashAttribute("order", savedOrder);
        redirectAttributes.addFlashAttribute("user", user);

        // Redirect the user to a confirmation page
        return "redirect:/user/order-confirmation";
    }

    @GetMapping("/order-confirmation")
    public String showOrderConfirmationPage(Model model) {
        // Check if the order and user are present in the model
        if (!model.containsAttribute("order") || !model.containsAttribute("user")) {
            return "redirect:/user/dashboard";
        }

        return "order-confirmation";
    }

}
