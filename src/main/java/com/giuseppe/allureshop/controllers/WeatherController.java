package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.WeatherResponse;
import com.giuseppe.allureshop.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather/{city}")
    public String getWeatherDirectly(@PathVariable String city, Model model) {
        WeatherResponse weather = weatherService.getWeather(city);
        model.addAttribute("weather", weather);
        return "weather";
    }

    @GetMapping("/weather")
    public String showWeatherForm(Model model) {
        model.addAttribute("city", "");
        model.addAttribute("country", "");
        return "weather-form";
    }

    @PostMapping("/weather")
    public String getWeather(@RequestParam("cityName") String city,
                             @RequestParam("countryName") String country,
                             Model model) {
        WeatherResponse weather = weatherService.getWeather(city + "," + country);
        model.addAttribute("weather", weather);
        model.addAttribute("city", city);
        model.addAttribute("country", country);
        return "weather";
    }
}
