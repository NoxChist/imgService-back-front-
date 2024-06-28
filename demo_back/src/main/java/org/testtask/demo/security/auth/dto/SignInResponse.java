package org.testtask.demo.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SignInResponse {
    private String principal;
    private String token;
}
