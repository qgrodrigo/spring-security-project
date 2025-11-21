package com.autonoma.service.impl;

import com.autonoma.dto.LoginRequest;
import com.autonoma.dto.LoginResponse;
import com.autonoma.model.Usuario;
import com.autonoma.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceTwo {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
    public String register(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);

        userRepository.save(user);

        return "User registered successfully";
    }
     **/

    public LoginResponse login(LoginRequest request){
        Usuario user = userRepository.findByUsuario(request.usuario());

        if (!passwordEncoder.matches(request.contraseña(), user.getContraseña())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsuario());

        return new LoginResponse(
                token,
                user.getUsuario(),
                "Login successful"
        );
    }
}
