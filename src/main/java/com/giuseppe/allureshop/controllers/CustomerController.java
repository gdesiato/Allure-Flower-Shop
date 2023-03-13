package com.giuseppe.allureshop.controllers;

import com.giuseppe.allureshop.exceptions.CustomerAlreadyExistsException;
import com.giuseppe.allureshop.models.Customer;
import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.services.CustomerService;
import com.giuseppe.allureshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String showNewCustomerPage(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "new-customer";
    }

    // As the Model is received back from the view, @ModelAttribute
    // creates a Customer based on the object you collected from
    // the HTML page above
//    @PostMapping(value ="/save")
//    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
//        customerService.saveCustomer(customer);
//        return "registration-confirmation";
//    }

//    @PostMapping(value = "/save")
//    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
//        String encodedPassword = passwordEncoder.encode(customer.getPassword());
//        customer.setPassword(encodedPassword);
//        customerService.saveCustomer(customer);
//        return "registration-confirmation";
//    }

    // Duplicate error
    @PostMapping(value = "/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {

        // Check if the customer already exists
        if (customerService.getCustomerByUsername(customer.getUsername()).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with username " + customer.getUsername() + " already exists.");
        }

        // Encode the password before saving the customer
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);

        // Create a User for the customer
        User user = new User();
        user.setUsername(customer.getUsername());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        // Set the User for the customer
        customer.setUser(user);

        // Save the customer and the user
        customerService.saveCustomer(customer);
        userService.saveUser(user);

        return "registration-confirmation";
    }


//    @PostMapping(value = "/save")
//    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
//
//        // Check if the customer already exists
//        if (customerService.getCustomerByUsername(customer.getUsername()).isPresent()) {
//            throw new CustomerAlreadyExistsException("Customer with username " + customer.getUsername() + " already exists.");
//        }
//
//        // Encode the password before saving the user
//        String encodedPassword = passwordEncoder.encode(customer.getUser().getPassword());
//
//        // Create a User for the customer
//        User user = new User();
//        user.setUsername(customer.getUsername());
//        user.setPassword(encodedPassword);
//        user.setEnabled(true);
//
//        // Set the User for the customer
//        customer.setUser(user);
//
//        // Save the customer and the user
//        customerService.saveCustomer(customer);
//        userService.saveUser(user);
//
//        return "registration-confirmation";
//    }



    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/";
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("update/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute("customer") Customer customer) {
        if (!customerService.getCustomerById(id).isPresent()) {
            return "redirect:/list";
        }
        customerService.updateCustomer(id, customer);
        return "redirect:/customer/" + id;
    }
}

