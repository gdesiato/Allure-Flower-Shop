package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Role;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.repositories.RoleRepository;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/user")
public class UserController implements ErrorController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;


    // imported methods from CustomerController

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

    //


    @GetMapping
    public String getUser(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        return "user-view"; // replace with the name of your view file
    }

    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register-user";
        }
        Role roleUser = roleRepository.findRoleByName("USER");
        user.setRoles(Collections.singletonList(roleUser));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String showUserDashboard(Model model, String username) {
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        return "user-dashboard-frag";
        //return "user-dashboard";
    }

}
