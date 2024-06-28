package org.testtask.demo.security.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.testtask.demo.security.auth.user.Role;


@RequiredArgsConstructor
@Getter
@Setter
public class RegisterDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
