package com.Forum.web;

import com.Forum.data.User;
import com.Forum.data.RoleRepository;
import com.Forum.data.UserRepository;
import com.Forum.security.RegistrationForm;
import com.Forum.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String registrationPage(@ModelAttribute("registrationForm") RegistrationForm registrationForm) {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm registrationForm) {
        registrationService.registerNewUser(registrationForm);
        return "redirect:/login";
    }
}
