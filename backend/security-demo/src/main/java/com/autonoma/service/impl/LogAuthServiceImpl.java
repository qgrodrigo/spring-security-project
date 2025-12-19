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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogAuthServiceImpl implements LogAuthService {

    private final LogAuthRepository logAuthRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public LogAuthResponse logAuth(Integer idUsuario, String usuarioIngresado, String ip,
                                   TipoEventoLogin evento, String tokenId,
                                   Boolean active, LocalDateTime expirationTime) {

        LogAuth log = new LogAuth();
        log.setFecha(LocalDate.now());
        log.setHora(LocalTime.now());
        log.setUsuarioIngresado(usuarioIngresado);
        log.setIpAddress(ip);
        log.setTipoEvento(evento);
        log.setTokenId(tokenId);
        log.setActive(active);
        log.setExpirationTime(expirationTime);

        if (idUsuario != null) {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
            log.setUsuario(usuario);
        }

        LogAuth saved = logAuthRepository.save(log);

        return mapToResponse(saved);
    }

    @Override
    public LogAuthResponse registrarLogout(String tokenId, String ip) {
        LogAuth log = logAuthRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        log.setActive(false);
        log.setTipoEvento(TipoEventoLogin.LOGOUT);
        log.setFecha(LocalDate.now());
        log.setHora(LocalTime.now());
        log.setIpAddress(ip);

        LogAuth saved = logAuthRepository.save(log);
        return mapToResponse(saved);
    }

    @Override
    public List<LogAuthResponse> getAllLogs() {
        return logAuthRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    public List<LogAuthResponse> getActiveSessions() {
        return logAuthRepository.findByActiveTrue().stream()
                .filter(log -> log.getExpirationTime() != null && log.getExpirationTime().isAfter(LocalDateTime.now()))
                .map(this::mapToResponse)
                .toList();
    }

    private LogAuthResponse mapToResponse(LogAuth log) {
        return new LogAuthResponse(
                log.getId(),
                log.getFecha(),
                log.getHora(),
                log.getUsuario() != null ? log.getUsuario().getId() : null,
                log.getUsuarioIngresado(),
                log.getIpAddress(),
                log.getTipoEvento().name(),
                log.getTokenId(),
                log.getActive(),
                log.getExpirationTime()
        );
    }
}
