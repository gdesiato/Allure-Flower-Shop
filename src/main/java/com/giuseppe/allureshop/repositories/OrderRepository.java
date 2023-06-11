package com.giuseppe.allureshop.repositories;

import com.giuseppe.allureshop.models.Order;
import com.giuseppe.allureshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findTopByUserOrderByCreatedAtDesc(User user);

}
