package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.services.FlowerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/flower")
public class FlowerController {


    @Autowired
    private FlowerServiceImpl flowerService;

    @GetMapping("/list")
    public String flowerHomePage(Model model) {
        final List<Flower> flowerList = flowerService.getAllFlowers();
        model.addAttribute("flowerList", flowerList);
        return "flowers";
    }

    @GetMapping("/new")
    public String newFlower(Model model) {
        Flower flower = new Flower();
        model.addAttribute("flower", flower);
        return "new-flower";
    }

    @PostMapping("/new")
    public String addNewFlower(@ModelAttribute Optional<Flower> flower) {
        flowerService.saveFlower(flower);
        return "redirect:/flowers"; // redirect to the flowers page
    }

    @RequestMapping("/delete/{id}")
    public String deleteFlower(@PathVariable(name = "id") Long id) {
        flowerService.deleteFlower(id);
        return "redirect:/";
    }

}
