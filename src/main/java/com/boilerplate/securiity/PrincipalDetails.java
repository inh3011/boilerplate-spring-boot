package com.boilerplate.securiity;

import com.boilerplate.infrastructure.entity.User;
import com.boilerplate.domain.enumuration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PrincipalDetails implements UserDetails {
    private Long id;

    @Getter
    private final String username;

    @JsonIgnore
    private String password;


    @Getter
    private final Role role;

    public PrincipalDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> rolesAndAuthorities = new HashSet<>();
        rolesAndAuthorities.add(role);

        return rolesAndAuthorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
