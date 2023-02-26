package com.giuseppe.allureshop.models;


import lombok.*;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flower")
@Builder
@Data
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;


}
