package com.autonoma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public record LoginResponse(
        String token,
        String username,
        String message
) {}
