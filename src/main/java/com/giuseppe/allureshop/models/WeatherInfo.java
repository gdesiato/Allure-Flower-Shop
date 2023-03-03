package com.giuseppe.allureshop.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherInfo {

    @JsonProperty("main")
    private String visual;
    private String description;
}
