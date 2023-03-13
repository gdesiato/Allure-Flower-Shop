package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public List<Customer> getAllCustomers();
    public Customer saveCustomer (Customer customer);
    public Optional<Customer> getCustomerById(Long id);
    public Optional<Customer> getCustomerByUsername(String username);
    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Long id, Customer customer);
    public void deleteCustomer(Long id);
    public List<Customer> saveAllCustomer(List<Customer> customerList);


}