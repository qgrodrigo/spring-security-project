package com.autonoma.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String SECRET;

    @Value("${security.jwt.ttl-minutes}")
    private long ttlMinutes;


}
