package com.Forum.web;

import com.Forum.User;
import com.Forum.data.RoleRepository;
import com.Forum.data.UserRepository;
import com.Forum.security.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.Arrays;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepo;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public String registrationPage(@ModelAttribute("registrationForm") RegistrationForm registrationForm) {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm registrationForm) {
        User newUser = registrationForm.toUser(passwordEncoder);
        newUser.setRoles(Arrays.asList(roleRepo.findByName("ROLE_USER")));
        userRepo.save(newUser);
        return "redirect:/login";
    }
}
