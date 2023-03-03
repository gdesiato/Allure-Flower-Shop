package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.models.WeatherResponse;
import com.giuseppe.allureshop.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather/{city}")
    @ResponseBody
    public WeatherResponse getWeather(@PathVariable String city) {
        return weatherService.getWeather(city);
    }
}
