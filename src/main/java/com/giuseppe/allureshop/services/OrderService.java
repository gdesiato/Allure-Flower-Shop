package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Cart;
import com.giuseppe.allureshop.models.CartItem;
import com.giuseppe.allureshop.models.Order;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.repositories.CartRepository;
import com.giuseppe.allureshop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Order createOrder(User user, Double totalAmount) {
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Order processOrderAndClearCart(Order order, Cart userCart) {
        List<CartItem> items = userCart.getItems();
        for (CartItem item : items) {
            order.addItem(item);
        }
        Order savedOrder = orderRepository.save(order);

        for (CartItem item : items) {
            item.setCart(null);
        }
        userCart.getItems().clear();
        cartRepository.save(userCart);
        return savedOrder;
    }

}
