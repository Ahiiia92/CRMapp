package com.crm.app.services;

import com.crm.app.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("User Service")
public interface UserService {
    List<User> getAllUsers();
}
