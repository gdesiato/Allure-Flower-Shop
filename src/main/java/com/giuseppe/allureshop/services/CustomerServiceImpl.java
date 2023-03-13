package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.Customer;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.repositories.CustomerRepository;
import com.giuseppe.allureshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

//    @Override
//    public Customer saveCustomer(Customer customer) {
//        return customerRepository.save(customer);
//    }

    @Override
    public Customer saveCustomer(Customer customer) {
        // Save the customer
        customerRepository.save(customer);

        // Create a new User
        User user = new User();
        user.setUsername(customer.getUsername());
        user.setPassword(passwordEncoder.encode(customer.getPassword()));
        user.setCustomer(customer);

        // Save the User
        userRepository.save(user);

        return customer;
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
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setAddress(customer.getAddress());
            return customerRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found with id " + id);
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> saveAllCustomer(List<Customer> customerList) {
        return customerRepository.saveAll(customerList);
    }

    @Override
    public Optional<Customer> getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
}
