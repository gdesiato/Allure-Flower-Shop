package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.services.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/flower")
public class FlowerController {


    @Autowired
    private FlowerService flowerService;

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

    @DeleteMapping("/{id}")
    public String removeFlower(@PathVariable(name = "id") Long flowerId) {
        Flower flower = FlowerService.getFlower(flowerId);
        //flower.setCustomer(null);
        flowerService.saveFlower(flower);
        return "redirect:/";
    }


}
