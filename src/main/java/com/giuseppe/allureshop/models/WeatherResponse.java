package com.giuseppe.allureshop.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WeatherResponse {

    @JsonProperty("weather")
    private List<WeatherInfo> weatherInfo;

    @JsonProperty("main")
    private MainData mainData;

}
