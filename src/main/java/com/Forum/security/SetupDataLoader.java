package com.Forum.security;

import com.Forum.User;
import com.Forum.data.PrivilegesRepository;
import com.Forum.data.RoleRepository;
import com.Forum.data.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PrivilegesRepository privilegesRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Privilege readPrivilege = createPriviligeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPriviligeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPriviliges = Arrays.asList(readPrivilege, writePrivilege);

        createRoleIfNotFound("ROLE_ADMIN", adminPriviliges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepo.findByName("ROLE_ADMIN");
        User user = new User(
                "admin",
                passwordEncoder.encode("password"),
                Arrays.asList(adminRole));
        userRepo.save(user);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPriviligeIfNotFound(String name) {

        Privilege privilege = privilegesRepo.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegesRepo.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> priviliges) {

        Role role = roleRepo.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(priviliges);
            roleRepo.save(role);
        }
        return role;
    }
}
