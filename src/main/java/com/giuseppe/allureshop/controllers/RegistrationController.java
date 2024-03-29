package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.Role;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.repositories.RoleRepository;
import com.giuseppe.allureshop.services.CartService;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class RegistrationController implements ErrorController {

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/new")
    public String showNewUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new-user";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);
        return "registration-confirmation";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("password-repeat") String confirmPassword, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null) {
            return "username-exists";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            return "password-mismatch";
        }

        Role userRole = roleRepository.findRoleByName("USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }
        user.setRoles(Collections.singleton(userRole));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.saveUser(user);

        Cart userCart = new Cart();
        userCart.setUser(user);
        userCart.setUsername(user.getUsername());
        cartService.saveCart(userCart);

        model.addAttribute("cart", userCart);
        model.addAttribute("items", userCart.getItems());
        model.addAttribute("username", user.getUsername());

        return "registration-confirmation";
    }
}
