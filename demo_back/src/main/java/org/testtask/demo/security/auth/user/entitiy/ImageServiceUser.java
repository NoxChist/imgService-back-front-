package org.testtask.demo.security.auth.user.entitiy;

import jakarta.persistence.*;
import lombok.*;
import org.testtask.demo.security.auth.user.Role;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(schema = "users", name = "user")
@Entity

public class ImageServiceUser {
    @Id
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
