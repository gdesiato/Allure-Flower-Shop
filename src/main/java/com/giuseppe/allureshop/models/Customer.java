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
@Getter
@Setter
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String address;
    private String password;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private User user;

}
