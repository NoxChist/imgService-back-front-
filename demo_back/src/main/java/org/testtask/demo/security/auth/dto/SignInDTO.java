package org.testtask.demo.security.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class SignInDTO {
    private String email;
    private String password;
}
