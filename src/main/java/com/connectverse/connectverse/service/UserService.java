package com.connectverse.connectverse.service;

import com.connectverse.connectverse.Repository.UserRepository;
import com.connectverse.connectverse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }
}
