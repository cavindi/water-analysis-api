package com.ludowica.waterqualityanalysisapi.services;

import com.ludowica.waterqualityanalysisapi.repository.RoleRepo;
import com.ludowica.waterqualityanalysisapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /*@Autowired
    PasswordEncoder encoder;*/

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    /*public void createUser(UserSignUp userSignUp) {


        User user = new User(
                userSignUp.getName(),
                userSignUp.getUsername(),
                userSignUp.getEmail(),
                encoder.encode(userSignUp.getPassword()));
        Set<String> strRoles = userSignUp.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {

            switch (role) {
                case "admin":
                    Role adminRole = roleRepo.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("User role not found"));
                    roles.add(adminRole);
                    break;
                case "mm":
                    Role mmRole = roleRepo.findByName(RoleName.ROLE_MM).orElseThrow(() -> new RuntimeException("User role not found"));
                    roles.add(mmRole);
                    break;
                case "mc":
                    Role mcRole = roleRepo.findByName(RoleName.ROLE_MC).orElseThrow(() -> new RuntimeException("User role not found"));
                    roles.add(mcRole);
                    break;
                case "supplier":
                    Role supplierRole = roleRepo.findByName(RoleName.ROLE_SUPPLIER).orElseThrow(() -> new RuntimeException("User role not found"));
                    roles.add(supplierRole);
                    break;
                case "external":
                    Role externalRole = roleRepo.findByName(RoleName.ROLE_EXTERNAL).orElseThrow(() -> new RuntimeException("User role not found"));
                    roles.add(externalRole);
                    break;
            }
        });

        user.setRoles(roles);
        userRepo.save(user);

    }*/
}