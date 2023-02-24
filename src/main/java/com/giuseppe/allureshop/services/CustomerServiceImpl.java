package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Customer;
import com.giuseppe.allureshop.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id)
                .orElse(null));
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
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> saveAllCustomer(List<Customer> customerList) {
        return customerRepository.saveAll(customerList);
    }
}
