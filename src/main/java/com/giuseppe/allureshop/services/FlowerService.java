package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Customer;
import com.giuseppe.allureshop.models.Flower;

import java.util.List;
import java.util.Optional;

public interface FlowerService {

    public List<Flower> getAllFlowers();
    public Flower updateFlower(Long id, Flower flower);
    public Flower saveFlower (Flower flower);
    public Optional<Flower> getFlowerById(Long id);
    public void deleteFlower(Long id);
}
