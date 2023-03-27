package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.repositories.RoleRepository;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/dashboard")
//    public String showUserDashboard(Model model, String username) {
//        User user = userService.getUser(username);
//        model.addAttribute("user", user);
//        return "user-dashboard-frag";
//        //return "user-dashboard";
//    }

//    @GetMapping("/dashboard")
//    public String showUserDashboard(Principal principal, Model model) {
//        if (principal != null) {
//            String username = principal.getName();
//            User user = userService.findByUsername(username);
//
//            if (user != null) {
//                model.addAttribute("user", user);
//                Cart userCart = cartService.findCartByUser(user);
//                model.addAttribute("cart", userCart);
//                model.addAttribute("items", userCart.getItems());
//            }
//        }
//
//        return "user-dashboard-frag";
//    }

    @GetMapping("/dashboard")
    public String showUserDashboard(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);

            if (user != null) {
                model.addAttribute("user", user);
                Cart userCart = cartService.findCartByUser(user);

                if (userCart == null) {
                    userCart = new Cart();
                    userCart.setUser(user);
                    userCart.setUsername(user.getUsername());
                    cartService.saveCart(userCart);
                }

                model.addAttribute("cart", userCart);
                model.addAttribute("items", userCart.getItems());
            }
        }

        return "user-dashboard-frag";
    }

}
