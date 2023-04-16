package com.giuseppe.allureshop.repositories;

import com.giuseppe.allureshop.models.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {

    List<Flower> findByName(String name);
}