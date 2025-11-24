package com.autonoma.dto.response;


public record LoginResponse(
        String token,
        long expiresIn
) {}
