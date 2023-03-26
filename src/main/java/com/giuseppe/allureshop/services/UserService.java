package com.giuseppe.allureshop.services;

import com.giuseppe.allureshop.models.User;
import com.giuseppe.allureshop.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;


    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElse(null));
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User optionalUser = userRepository.findByUsername(username);
        if (optionalUser == null) {
            throw new UsernameNotFoundException(username + " is not a valid username! Check for typos and try again.");
        }
        return (UserDetails) optionalUser;
    }

    @Transactional(readOnly = true)
    public User getUserByUserId(Long userId) throws EntityNotFoundException {
        User user = userRepository.getById(userId);
        //call unproxy() to ensure all related entities are loaded—no lazy load exceptions.
        return (User) Hibernate.unproxy(user);
    }

    @Transactional(readOnly = true)
    public User getUser(String username) throws EntityNotFoundException  {
        return userRepository.findByUsername(username);
    }


    private void checkPassword(String password) {
        if (password == null) {
            throw new IllegalStateException("You must set a password");
        }
        if (password.length() < 6) {
            throw new IllegalStateException("Password is too short. Must be longer than 6 characters");
        }
    }

}