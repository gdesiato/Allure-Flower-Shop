package com.giuseppe.allureshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherResponse {

    private String temperature;
    private String description;
}
