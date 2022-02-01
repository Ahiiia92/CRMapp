package com.crm.app.services;

import com.crm.app.exceptions.NoUserException;
import com.crm.app.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class InMemoryUsers implements UserService {

    Map<String, User> users = new HashMap<>();

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User createUser(User user) {
        return users.put(user.getId().toString(), user);
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> optUser = Optional.ofNullable(users.get(userId));
        if(optUser.isPresent())
            return optUser.get();
        else
            throw new NoUserException(userId);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return users
                .values()
                .stream()
                .filter(u -> Objects.equals(username, u.getUsername()))
                .findFirst();
    }

    @Override
    public User updateUser(Integer userId, User updateUser) {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}
