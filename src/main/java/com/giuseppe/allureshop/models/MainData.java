package com.giuseppe.allureshop.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MainData {

    private double temp;
    private double feels_like;
    private int humidity;

    public void setTemp(double temp) {
        this.temp = TemperatureUtil.kelvinToCelsius(temp);
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = TemperatureUtil.kelvinToCelsius(feels_like);
    }

}
