package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.services.FlowerService;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    private FlowerService flowerService;


    @GetMapping
    public String getUser(Model model, Authentication authentication) {
        User admin = (User) authentication.getPrincipal();
        model.addAttribute("username", admin.getUsername());
        model.addAttribute("roles", admin.getAuthorities());
        return "admin-view";
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model, String username) {
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "admin-dashboard";
    }


    @GetMapping("flowers/list")
    public String flowerHomePage(Model model) {
        final List<Flower> flowerList = flowerService.getAllFlowers();
        model.addAttribute("flowerList", flowerList);
        return "flowers";
    }

    @GetMapping("flowers/new")
    public String newFlower(Model model) {
        Flower flower = new Flower();
        model.addAttribute("flower", flower);
        return "new-flower";
    }

    @PostMapping("flowers/new")
    public String addNewFlower(@ModelAttribute Flower flower) {
        flowerService.saveFlower(flower);
        return "redirect:/admin/flowers/list";
    }

    @RequestMapping("flowers/delete/{id}")
    public String deleteFlower(@PathVariable(name = "id") Long id) {
        flowerService.deleteFlower(id);
        return "redirect:/admin/flowers/list";
    }

    // Flower Update

    @GetMapping("/flowers/update/{id}")
    public String showUpdateFlowerPage(@PathVariable("id") Long id, Model model) {
        Flower flower = flowerService.getFlowerById(id);
        model.addAttribute("flower", flower);
        return "update-flower";
    }

    @PostMapping("/flowers/update")
    public String updateFlower(@ModelAttribute Flower flower) {
        flowerService.updateFlower(flower);
        return "redirect:/admin/flowers/list";
    }

}
