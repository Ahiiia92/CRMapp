package com.crm.app.services;

import com.crm.app.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public interface UserService {
    List<User> getAllUsers();

    User findUserByUsername(String username);

    User findUserById(Integer userId);

    User createUser(User user);
}
