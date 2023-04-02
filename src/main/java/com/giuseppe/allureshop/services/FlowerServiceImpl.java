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
public class FlowerServiceImpl implements FlowerService {

    @Autowired
    FlowerRepository flowerRepository;


    @Override
    @Cacheable(value = "flowers", key = "'all'")
    public List<Flower> getAllFlowers() {
        return flowerRepository.findAll();
    }


    @Override
    @Cacheable(value = "flowers", key = "#id")
    public Optional<Flower> getFlowerById(Long id) {
        return Optional.ofNullable(flowerRepository.findById(id)
                .orElse(null));
    }

    @Override
    @CacheEvict(value = "flowers", allEntries = true)
    public void deleteFlower(Long id) {
        flowerRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "flowers", key = "#id")
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

    @Override
    @CachePut(value = "flowers", key = "#result.id")
    public Flower saveFlower(Flower flower) {
        return flowerRepository.save(flower);
    }

}
