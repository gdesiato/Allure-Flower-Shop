package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.models.FlowerComposition;
import com.giuseppe.allureshop.repositories.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FlowerService {

    @Autowired
    private static FlowerRepository flowerRepository;


    public List<Flower> getAllFlowers() {
        return flowerRepository.findAll();
    }


    public FlowerComposition createComposition(List<Long> flowerIds) {
        return null;
    }

    public static Flower getFlower(Long id) {
        return flowerRepository.findById(id)
                .orElse(null);
    }


    public boolean deleteFlower(Long id) {
        Optional<Flower> flower = flowerRepository.findById(id);
        if (flower.isPresent()) {
            flowerRepository.delete(flower.get());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Flower saveFlower(Flower flower) throws IllegalArgumentException {
        return flowerRepository.save(flower);
    }
}
