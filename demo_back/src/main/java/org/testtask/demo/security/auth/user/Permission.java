package org.testtask.demo.security.auth.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    REGISTER_ADMIN("REGISTER_ADMIN");

    private final String value;
}
