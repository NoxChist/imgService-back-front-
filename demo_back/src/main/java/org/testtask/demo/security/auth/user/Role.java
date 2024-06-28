package org.testtask.demo.security.auth.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.testtask.demo.security.auth.user.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(CREATE, READ, DELETE, UPDATE, REGISTER_ADMIN)),
    USER(Set.of(CREATE, READ, DELETE));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<>(getPermissions().stream().map(permission ->
                new SimpleGrantedAuthority(permission.getValue())).toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
