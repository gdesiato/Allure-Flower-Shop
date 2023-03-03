package com.giuseppe.allureshop.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@Data
@Entity
public class Cart {

    private User user;
    private List<Flower> flowerList;


}
