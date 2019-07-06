package com.ludowica.waterqualityanalysisapi.services;

import com.ludowica.waterqualityanalysisapi.models.Role;
import com.ludowica.waterqualityanalysisapi.models.RoleName;
import com.ludowica.waterqualityanalysisapi.models.User;
import com.ludowica.waterqualityanalysisapi.models.UserSignUp;
import com.ludowica.waterqualityanalysisapi.repository.RoleRepo;
import com.ludowica.waterqualityanalysisapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    public void createUser(UserSignUp userSignUp) {


        User user = new User(userSignUp.getName(), userSignUp.getUsername(), userSignUp.getEmail(), encoder.encode(userSignUp.getPassword()));
        Set<String> strRoles = userSignUp.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles != null) {

            strRoles.forEach(role -> {

                switch (role) {
                    case "admin":
                        Role adminRole = roleRepo.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("User role not found"));
                        roles.add(adminRole);
                        break;
//                case "customer":
//                    Role userRole = roleRepo.findByName(RoleName.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("User role not found"));
//                    roles.add(userRole);
//                    break;
                }
            });

        }

        user.setRoles(roles);
        userRepo.save(user);

    }

    public User updateUser(User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}