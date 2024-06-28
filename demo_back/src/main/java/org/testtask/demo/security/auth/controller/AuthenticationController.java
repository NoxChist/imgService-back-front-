package org.testtask.demo.security.auth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.testtask.demo.security.auth.dto.SignInDTO;
import org.testtask.demo.security.auth.dto.SignInResponse;
import org.testtask.demo.security.auth.service.IAuthenticationService;
import org.testtask.demo.security.auth.dto.RegisterDTO;


@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private final IAuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        System.out.println("hello from reg");
        return service.register(registerDTO);
    }

    @PostMapping("/register-moderator")
    public ResponseEntity<String> registerModerator(@RequestBody RegisterDTO registerDTO) {
        return service.registerAdmin(registerDTO);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInDTO signInDTO) {
        return service.signIn(signInDTO);
    }

}
