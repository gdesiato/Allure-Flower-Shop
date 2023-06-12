package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.exceptions.FlowerNotFoundException;
import com.giuseppe.allureshop.models.Flower;
import com.giuseppe.allureshop.repositories.FlowerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(FlowerService.class);


    @Cacheable(value = "flowers")
    public List<Flower> getAllFlowers() {
        LOGGER.info("Fetching all flowers from the database");
        return flowerRepository.findAll();
    }

    @Cacheable(value = "flowers", key = "#id")
    public Flower getFlowerById(Long id) {
        LOGGER.info("Fetching flower with id {} from the database", id);
        return flowerRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "flowers", key = "#id")
    public void deleteFlower(Long id) {
        LOGGER.info("Deleting flower with id {} from the database", id);
        flowerRepository.deleteById(id);
    }

    @CachePut(value = "flowers", key = "#id")
    public Flower updateFlower(Long id, Flower flower) {
        LOGGER.info("Updating flower with id {} in the database", id);
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

    @CachePut(value = "flowers", key = "#flower.id")
    public Flower saveFlower(Flower flower) {
        return flowerRepository.save(flower);
    }

    public void saveFlowers(List<Flower> flowers) {
        flowerRepository.saveAll(flowers);
    }

    @CachePut(value = "flowers", key = "#flower.id")
    public void updateFlower(Flower flower) {
        Flower existingFlower = flowerRepository.findById(flower.getId())
                .orElseThrow(() -> new FlowerNotFoundException("Sorry, there is no flower found with id " + flower.getId()));

        existingFlower.setName(flower.getName());
        existingFlower.setPrice(flower.getPrice());

        flowerRepository.save(existingFlower);
    }

}
