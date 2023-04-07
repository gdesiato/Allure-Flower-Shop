package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Flower;
import org.springframework.cache.annotation.CachePut;

import java.util.List;
import java.util.Optional;

public interface FlowerService {

    public List<Flower> getAllFlowers();
    public Flower updateFlower(Long id, Flower flower);
    public Optional<Flower> saveFlower (Optional<Flower> flower);
    public Optional<Flower> getFlowerById(Long id);
    public void deleteFlower(Long id);

    @CachePut(value = "flowers", key = "#result.id")
    Flower saveFlower(Flower flower);
}
