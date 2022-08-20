package com.crm.app.services;

import com.crm.app.models.User;
import com.crm.app.repositories.UserRepository;
import com.crm.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public User editUser(User user) {
        Optional<User> optUser = userRepo.findById(user.getId());
        if (optUser.isPresent()) {
            return userRepo.save(user);
        } else {
            throw new UserNotFoundException(user.getId()); }
    }

    @Override
    public void removeUser(Integer userId) {
        Optional<User> optUser = userRepo.findById(userId);
        optUser.ifPresent(user -> userRepo.delete(user));
    }

    public User getCurrentUser() {
        String currentUsername = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            currentUsername = auth.getName();
        }
        return findUserByUsername(currentUsername);
    }
}
