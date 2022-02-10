package com.crm.app.services;

import com.crm.app.models.User;
import com.crm.app.repositories.UserRepository;
import com.crm.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceDBImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> optUser = userRepo.findUserByUsername(username);
        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new UserNotFoundException(username);
        }
    }

    @Override
    public User findUserById(Integer userId) {
        Optional<User> optUser = userRepo.findById(userId);
        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new UserNotFoundException(userId); }
    }

    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }

//    TODO: void removeUser(Integer user_id);
//    TODO: void editUser(User user);
}
