package com.giuseppe.allureshop.models;


import lombok.*;

import javax.persistence.*;

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
    private Integer age;
    private String address;

//    @OneToMany
//    private FlowerComposition flowerComposition;

}
