package com.autonoma.security;

public class SecurityEndpoints {

    public static final String[] PUBLIC = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/login"
    };

    public static final String[] ADMIN = {
            "/api/v1/movimientos/**",
            "/api/v1/usuarios/**",
            "/api/v1/personals/**",
            "/api/v1/roles/**"
    };

    public static final String[] USER = {
            "/api/v1/movimientos/**",
            "/api/v1/usuarios/**",
            "/api/v1/personals/**",
            "/api/v1/roles/**"
    };
}
