package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.exceptions.FlowerNotFoundException;
import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.repositories.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlowerService {

    @Autowired
    FlowerRepository flowerRepository;

    //@Cacheable(value = "flowers")
    public List<Flower> getAllFlowers() {
        return flowerRepository.findAll();
    }

    //@Cacheable(value = "flower", key = "#id")
    public Flower getFlowerById(Long id) {
        return flowerRepository.findById(id).orElse(null);
    }

    //@CacheEvict(value = "flower", key = "#id")
    public void deleteFlower(Long id) {
        flowerRepository.deleteById(id);
    }

    public Flower updateFlower(Long id, Flower flower) {
        Optional<Flower> optionalFlower = flowerRepository.findById(id);
        if (optionalFlower.isPresent()) {
            Flower existingFlower = optionalFlower.get();
            existingFlower.setName(flower.getName());
            existingFlower.setPrice(flower.getPrice());
            return flowerRepository.save(existingFlower);
        } else {
            throw new FlowerNotFoundException("Sorry, there is no flower found with id " + id);
        }
    }

    //@CachePut(value = "flower", key = "#flower.id")
    public Flower saveFlower(Flower flower) {
        return flowerRepository.save(flower);
    }

    public void saveFlowers(List<Flower> flowers) {
        flowerRepository.saveAll(flowers);
    }

    public void updateFlower(Flower flower) {
        Flower existingFlower = flowerRepository.findById(flower.getId())
                .orElseThrow(() -> new FlowerNotFoundException("Sorry, there is no flower found with id " + flower.getId()));

        existingFlower.setName(flower.getName());
        existingFlower.setPrice(flower.getPrice());

        flowerRepository.save(existingFlower);
    }

}
