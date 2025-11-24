package com.autonoma.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {

    @Value("${security.jwt.secret}")
    private String SECRET;

    @Value("${security.jwt.ttl-minutes}")
    private long expiration; // lifetime in minutes

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Generate token
    public String generateToken(UserDetails user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(expiration));

        return Jwts.builder()
                .setSubject(user.getUsername()) // sub
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .setIssuer("higienecheck-api") // iss
                .setAudience("higienecheck-client") // aud
                .setIssuedAt(Date.from(now)) // iat
                .setExpiration(Date.from(expiry)) // exp
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // stable
                .compact();
    }

    // Extract claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // stable
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Validate token
    public boolean isValid(String token, UserDetails user) {
        Claims claims = extractAllClaims(token);
        return user.getUsername().equals(claims.getSubject())
                && claims.getExpiration().after(new Date());
    }

    public long getExpirationMinutes() {
        return expiration;
    }
}
