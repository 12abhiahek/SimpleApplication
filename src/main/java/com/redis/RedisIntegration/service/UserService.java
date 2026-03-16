package com.redis.RedisIntegration.service;

import com.redis.RedisIntegration.entity.User;
import com.redis.RedisIntegration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE USER
    public User createUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User object cannot be null");
        }

        if (user.getUserid() == null || user.getUserid().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }

        userRepository.saveUser(user);
        return user;
    }

    // GET USER BY ID
    public User getUserById(String userId) {

        User user = userRepository.getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return user;
    }

    // UPDATE USER
    public User updateUser(User user) {

        if (user == null || user.getUserid() == null) {
            throw new IllegalArgumentException("User or User ID cannot be null");
        }

        User existingUser = userRepository.getUserById(user.getUserid());

        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + user.getUserid());
        }

        return userRepository.updateUser(user);
    }

    // DELETE USER
    public void deleteUser(String userId) {

        User existingUser = userRepository.getUserById(userId);

        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        userRepository.deleteUser(userId);
    }

    // GET ALL USERS
    public List<User> getAllUsers() {

        List<User> users = userRepository.findAllUsers();

        if (users == null || users.isEmpty()) {
            throw new RuntimeException("No users found");
        }

        return users;
    }
}
