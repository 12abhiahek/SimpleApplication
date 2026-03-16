package com.redis.RedisIntegration.repository;

import com.redis.RedisIntegration.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private static final String USER_KEY_PREFIX = "USER:";

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    public UserRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveUser(User user) {
        redisTemplate.opsForValue().set(USER_KEY_PREFIX + user.getUserid(), user);
    }

    public User getUserById(String userid) {
        return (User) redisTemplate.opsForValue().get(USER_KEY_PREFIX + userid);
    }

    public List<User> findAllUsers() {
        return redisTemplate.keys(USER_KEY_PREFIX + "*").stream()
                .map(key -> (User) redisTemplate.opsForValue().get(key))
                .toList();
    }

    public void deleteUser(String userid) {
        redisTemplate.delete(USER_KEY_PREFIX + userid);
    }

    public User updateUser(User user) {
        saveUser(user);
        return user;
    }

    public List<User> findAll() {
        return redisTemplate.opsForHash().values(USER_KEY_PREFIX)
                .stream()
                .map(obj -> (User) obj)
                .toList();
    }

}
