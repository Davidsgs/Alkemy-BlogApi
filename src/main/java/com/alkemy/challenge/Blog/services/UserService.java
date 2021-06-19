package com.alkemy.challenge.Blog.services;

import com.alkemy.challenge.Blog.models.User;
import com.alkemy.challenge.Blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException("User with email: " + email + " not found")
        );
    }

}
