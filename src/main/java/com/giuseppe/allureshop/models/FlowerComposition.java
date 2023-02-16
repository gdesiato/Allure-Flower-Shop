package com.giuseppe.allureshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flowerComposition")
@Builder
@Data
public class FlowerComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private List<Flower> flowers;
    private double price;

}
