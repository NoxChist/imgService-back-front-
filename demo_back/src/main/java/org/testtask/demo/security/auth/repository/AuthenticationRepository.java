package org.testtask.demo.security.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testtask.demo.security.auth.user.entitiy.ImageServiceUser;

import java.util.Optional;

@Repository
public interface AuthenticationRepository  extends JpaRepository<ImageServiceUser, String> {
    Optional<ImageServiceUser> findByEmail(String email);
}
