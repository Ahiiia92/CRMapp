package com.crm.app.services;

import com.crm.app.exceptions.NoUserException;
import com.crm.app.models.User;
import com.crm.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component()
public class UserServiceDBImpl implements UserDetailsService, UserService {
    private UserRepository userRepository;

    public UserServiceDBImpl(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> optUser = userRepository.findById(Integer.parseInt(userId));
        if(optUser.isPresent())
            return optUser.get();
        else
            throw new NoUserException(userId);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return null;
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
        Optional<User> userToDelete = userRepository.findById(id);
        userRepository.delete(userToDelete.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findUserByUsername(username);
        if(optUser.isPresent())
            return new org.springframework.security.core.userdetails.User(optUser.get().getUsername(), optUser.get().getPassword(), getAuthority());
        else
            throw new NoUserException("Wrong Credentials");
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
