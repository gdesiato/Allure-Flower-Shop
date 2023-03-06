package com.giuseppe.allureshop.repositories;

import com.giuseppe.allureshop.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUsername(String username);
}