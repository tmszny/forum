package com.Forum.security;

import com.Forum.User;
import com.Forum.data.RoleRepository;
import com.Forum.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;
    private RoleRepository roleRepo;

    @Autowired
    public UserRepositoryUserDetailsService(
            UserRepository userRepo,
            RoleRepository roleRepo) {

        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true,
                    true,
                    true,
                    getAuthorities(user.getRoles())
                    );
        }

        throw new UsernameNotFoundException("Użytkownik " + username + " nie został odnaleziony.");
    }

    // TODO CO DO CHUJA DZIEJE SIĘ PONIŻEJ ???
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthority(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthority(List<String> priviliges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilige : priviliges) {
            authorities.add(new SimpleGrantedAuthority(privilige));
        }
        return authorities;
    }
}
