package com.autonoma.dto.response;


public record LoginResponse(
        String token,
        String username,
        String message
) {}
