package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.models.FlowerComposition;

import java.util.List;
import java.util.Optional;

public interface FlowerShopService {
    public List<Flower> getAllFlowers();
    public Optional<Flower> getFlowerById(long id);
    public FlowerComposition createComposition(List<Long> flowerIds);
}
