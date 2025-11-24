package com.autonoma.service.impl;

import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.Estado;
import com.autonoma.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByUsuario(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        if (user.getEstado() != Estado.ACTIVO){
            throw new UsernameNotFoundException("Usuario inactivo");
        }

        return User.builder()
                .username(user.getUsuario())
                .password(user.getContraseña())
                .roles(user.getRol().getNombre())
                .build();
    }
}
