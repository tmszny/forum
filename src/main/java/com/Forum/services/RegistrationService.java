package com.Forum.services;

import com.Forum.data.RoleRepository;
import com.Forum.data.User;
import com.Forum.data.UserRepository;
import com.Forum.security.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void registerNewUser(RegistrationForm registrationForm) {
        User newUser = registrationForm.toUser(passwordEncoder);
        newUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(newUser);
    }
}
