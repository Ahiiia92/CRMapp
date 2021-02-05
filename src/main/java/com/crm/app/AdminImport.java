package com.crm.app;

import com.crm.app.models.Role;
import com.crm.app.models.User;
import com.crm.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminImport implements ApplicationRunner {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // SuperAdmin can manage all Users = developer level
        User superAdmin = new User();

        Role roleSuperAdmin = new Role();
        roleSuperAdmin.setRoleName("SuperAdmin");

        superAdmin.setUsername("superAdmin");
        superAdmin.setEmail("sa@sa.de");
        superAdmin.setPassword(encoder.encode("test123"));
        superAdmin.setRole(roleSuperAdmin);

        userRepository.save(superAdmin);

        // Admin is the Sales Manager that can manage its contacts and sale process
        User admin = new User();

        Role roleAdmin = new Role();
        roleAdmin.setRoleName("Admin");

        admin.setEmail("a@a.fr");
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("test123"));
        admin.setRole(roleAdmin);

        userRepository.save(admin);
    }
}
