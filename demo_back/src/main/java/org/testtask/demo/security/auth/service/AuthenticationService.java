package org.testtask.demo.security.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.testtask.demo.security.auth.ImageServiceUserDetails;
import org.testtask.demo.security.auth.dto.RegisterDTO;
import org.testtask.demo.security.auth.dto.SignInDTO;
import org.testtask.demo.security.auth.repository.AuthenticationRepository;
import org.testtask.demo.security.auth.dto.SignInResponse;
import org.testtask.demo.security.auth.user.Role;
import org.testtask.demo.security.auth.user.entitiy.ImageServiceUser;
import org.testtask.demo.security.jwt.JwtManager;

import java.util.Optional;

import static org.testtask.demo.security.auth.user.Role.ADMIN;
import static org.testtask.demo.security.auth.user.Role.USER;


@RequiredArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService{

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationRepository repository;
    private final PasswordEncoder encoder;
    private final JwtManager jwtManager;

    @Override
    public ResponseEntity<String> register(RegisterDTO data) {
        ImageServiceUser user = buildUser(data);
        user.setRole(USER);
        repository.save(user);
        return new ResponseEntity<>(
                String.format("Registration successful! %s | %s", user.getEmail(), user.getRole()), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> registerAdmin(RegisterDTO data) {
        ImageServiceUser user = buildUser(data);
        user.setRole(ADMIN);
        repository.save(user);
        return new ResponseEntity<>(
                String.format("Registration successful! %s | %s", user.getEmail(), user.getRole()), HttpStatus.OK);


    }

    @Override
    public ResponseEntity<SignInResponse> signIn(SignInDTO data) {
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ImageServiceUserDetails principal = (ImageServiceUserDetails ) authentication.getPrincipal();
        String firstname = principal.imageServiceUser().getFirstname();

        String token = jwtManager.generateToken(authentication);
        return new ResponseEntity<>(new SignInResponse(firstname, token), HttpStatus.OK);

    }
    public ImageServiceUser getImageServiceUser(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    private ImageServiceUser buildUser(RegisterDTO data) {
        return ImageServiceUser.builder()
                .firstname(data.getFirstname())
                .lastname(data.getLastname())
                .email(data.getEmail())
                .password(encoder.encode(data.getPassword()))
                .build();
    }
}
