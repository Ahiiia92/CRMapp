package com.crm.app.services;

import com.crm.app.models.User;
import com.crm.app.repositories.UserRepository;
import com.crm.app.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsServiceDBImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails details;
        Optional<User> oprUser = userRepo.findUserByUsername(username);
        if(oprUser.isPresent())
            details = new MyUserDetails(oprUser.get());
        else
            throw new UsernameNotFoundException(username);
        return details;
    }
}
