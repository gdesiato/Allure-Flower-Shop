package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.models.FlowerComposition;
import com.giuseppe.allureshop.repositories.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlowerShopServiceImpl implements FlowerShopService {

    @Autowired
    private FlowerRepository flowerRepository;

    @Override
    public List<Flower> getAllFlowers() {
        return flowerRepository.findAll();
    }

    @Override
    public Optional<Flower> getFlowerById(long id) {
        return flowerRepository.findById(id);
    }

    @Override
    public FlowerComposition createComposition(List<Long> flowerIds) {
        return null;
    }
}