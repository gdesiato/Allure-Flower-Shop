package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.models.FlowerComposition;

import java.util.List;
import java.util.Optional;

public interface FlowerShopService {
    List<Flower> getAllFlowers();
    Optional<Flower> getFlowerById(long id);
    FlowerComposition createComposition(List<Long> flowerIds);
}
