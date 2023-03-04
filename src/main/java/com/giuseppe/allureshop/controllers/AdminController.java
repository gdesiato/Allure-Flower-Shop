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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController implements ErrorController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser(Model model, Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        model.addAttribute("username", admin.getUsername());
        model.addAttribute("roles", admin.getAuthorities());
        return "admin-view"; // replace with the name of your view file
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model, String username) {
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        return "admin-dashboard";
        //return "admin-dashboard";
    }
}
