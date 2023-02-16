package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService{
    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return Optional.empty();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {

    }
}
