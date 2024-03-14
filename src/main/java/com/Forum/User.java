package com.Forum;

import com.Forum.security.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name = "User_Table")
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private final String username;
    private final String email;
    private final String password;

    private boolean isAccountNonLocked = true;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    // TODO adapt for new users
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String username, String password, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = "mail@mail.com"; // TODO change to be retrieved from the form
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = "mail@mail.com"; // TODO change to be retrieved from the form
    }

    // TODO assign admin permissions
    public void admin() {
    }

    public void blockAccount() { this.isAccountNonLocked = false;
    }
}
