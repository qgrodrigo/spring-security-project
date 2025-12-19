package com.autonoma.service;

import com.autonoma.dto.response.LogAuthResponse;
import com.autonoma.model.enums.TipoEventoLogin;

import java.time.LocalDateTime;
import java.util.List;

public interface LogAuthService {

    LogAuthResponse logAuth(Integer idUsuario, String usuarioIngresado, String ip, TipoEventoLogin evento,
                            String tokenId, Boolean active, LocalDateTime expirationTime);

    LogAuthResponse registrarLogout(String tokenId, String ip);

    List<LogAuthResponse> getAllLogs();

    List<LogAuthResponse> getActiveSessions();
}
