package org.testtask.demo.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.testtask.demo.security.auth.user.entitiy.ImageServiceUser;

import java.util.Collection;

public record ImageServiceUserDetails(ImageServiceUser imageServiceUser) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return imageServiceUser.getRole().getAuthorities();
    }

    @Override
    public String getPassword() {
        return imageServiceUser.getPassword();
    }

    @Override
    public String getUsername() {
        return imageServiceUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
