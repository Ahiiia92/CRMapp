package com.crm.app.services;

import com.crm.app.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public interface UserService {
    List<User> getUsers();

    User createUser(User user);

    User getUserById(Integer userId);

    User updateUser(Integer userId, User updateUser);

    void deleteUser(User user);

    void deleteById(Integer id);
}
