package com.giusepppe.allureshop.services;

import com.giusepppe.allureshop.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
}