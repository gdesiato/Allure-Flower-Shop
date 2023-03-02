package com.giuseppe.allureshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherResponse {

    private double temp;
    private int humidity;
}
