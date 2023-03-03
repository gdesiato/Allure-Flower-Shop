package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    // @Value will look in our application.property file for this specific property
    // if it will find the property, it will assign it to this value
    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String weatherUrl = apiUrl + city + "&appid=" + apiKey;
        WeatherResponse response = restTemplate.getForObject(weatherUrl, WeatherResponse.class);
        return response;
    }
}
