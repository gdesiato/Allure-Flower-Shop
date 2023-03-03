package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.WeatherResponse;
import com.giuseppe.allureshop.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather/{city}")
    public String getWeather(@PathVariable String city, Model model) {
        WeatherResponse weather = weatherService.getWeather(city);
        model.addAttribute("weather", weather);
        return "weather"; // return the name of the Thymeleaf template to be rendered
    }
}
