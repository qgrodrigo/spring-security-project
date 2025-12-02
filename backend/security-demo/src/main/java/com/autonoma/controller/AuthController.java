package com.autonoma.controller;

import com.autonoma.dto.request.LoginRequest;
import com.autonoma.dto.response.LoginResponse;
import com.autonoma.model.entity.Personal;
import com.autonoma.model.entity.Usuario;
import com.autonoma.repository.PersonalRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.security.JwtService;
import com.autonoma.service.AuthService;
import com.autonoma.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
