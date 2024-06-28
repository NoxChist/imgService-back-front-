package org.testtask.demo.security.auth.service;

import org.springframework.http.ResponseEntity;
import org.testtask.demo.security.auth.dto.RegisterDTO;
import org.testtask.demo.security.auth.dto.SignInDTO;
import org.testtask.demo.security.auth.dto.SignInResponse;
import org.testtask.demo.security.auth.user.entitiy.ImageServiceUser;

import java.util.Optional;


public interface IAuthenticationService {
    ResponseEntity<String> register(RegisterDTO data);

    ResponseEntity<String> registerAdmin(RegisterDTO data);

    ResponseEntity<SignInResponse> signIn(SignInDTO data);

    ImageServiceUser getImageServiceUser(String email);

}
