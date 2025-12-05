package com.autonoma.service.impl;

import com.autonoma.dto.response.LogAuthResponse;
import com.autonoma.exception.ResourceNotFoundException;
import com.autonoma.exception.UsuarioInactivoException;
import com.autonoma.model.entity.LogAuth;
import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.TipoEventoLogin;
import com.autonoma.repository.LogAuthRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.service.LogAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogAuthServiceImpl implements LogAuthService {

    private final LogAuthRepository logAuthRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public LogAuthResponse logAuth(Integer idUsuario, String usuarioIngresado, String ip, TipoEventoLogin evento) {

        LogAuth log = new LogAuth();
        log.setFecha(LocalDate.now());
        log.setHora(LocalTime.now());
        log.setUsuarioIngresado(usuarioIngresado);
        log.setIpAddress(ip);
        log.setTipoEvento(evento);

        if (idUsuario != null) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElse(null);
            log.setUsuario(usuario);
        }

        LogAuth saved = logAuthRepository.save(log);

        return new LogAuthResponse(
                saved.getId(),
                saved.getFecha(),
                saved.getHora(),
                saved.getUsuario() != null ? saved.getUsuario().getId() : null,
                saved.getUsuarioIngresado(),
                saved.getIpAddress(),
                saved.getTipoEvento().name()
        );
    }


    @Override
    public List<LogAuthResponse> getAllLogs() {
        return logAuthRepository.findAll().stream()
                .map(log -> new LogAuthResponse(
                        log.getId(),
                        log.getFecha(),
                        log.getHora(),
                        log.getUsuario() != null ? log.getUsuario().getId() : null,
                        log.getUsuarioIngresado(),
                        log.getIpAddress(),
                        log.getTipoEvento().name()
                ))
                .toList();

    }
}
