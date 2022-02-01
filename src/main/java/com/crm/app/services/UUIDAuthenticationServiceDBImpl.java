package com.crm.app.services;

import com.crm.app.exceptions.NoUserException;
import com.crm.app.models.User;
import com.crm.app.repositories.UserRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UUIDAuthenticationServiceDBImpl implements UserAuthenticationService {
    @NonNull
    UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Override
    public Optional<String> login(String username, String password) {
        final String uuid = UUID.randomUUID().toString();
        final User user = User
                .builder()
                .id(Integer.parseInt(uuid))
                .username(username)
                .password(password)
                .build();
        userRepo.save(user);
        return Optional.of(uuid);
    }

    @Override
    public Optional<User> findByToken(String token) {
        Optional<User> optUser = userRepo.findById(Integer.parseInt(token));
        if (optUser.isPresent())
            return optUser;
        else
            throw new NoUserException("No user found with this token");
    }

    @Override
    public void logout(User user) {

    }
}
