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

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;


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

    public User createNewUser(User user) {
        user.setId(null);
//        user.getAuthorities().forEach(a -> a.setId(null));

        //override or set user settings to correct values
//        user.setAccountNonExpired(true);
//        userDetails.setAccountNonLocked(true);
//        userDetails.setCredentialsNonExpired(true);
//        userDetails.setEnabled(true);
//        user.setAuthorities(Collections.singletonList(new Role(Role.Roles.ROLE_USER)));

        checkPassword(user.getPassword());
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e.getCause());
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new IllegalStateException("You must set a password");
        }
        if (password.length() < 6) {
            throw new IllegalStateException("Password is too short. Must be longer than 6 characters");
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }
}