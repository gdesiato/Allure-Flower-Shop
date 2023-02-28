package com.giuseppe.allureshop.models;


import lombok.*;

import javax.persistence.*;

/**
 * Represents a customer model in the flower shop.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Builder
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;


//    @OneToMany
//    private FlowerComposition flowerComposition;

}
