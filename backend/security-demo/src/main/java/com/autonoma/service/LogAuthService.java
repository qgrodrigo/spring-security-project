package com.autonoma.service;

import com.autonoma.dto.response.LogAuthResponse;
import com.autonoma.model.enums.TipoEventoLogin;

import java.util.List;

public interface LogAuthService {
    LogAuthResponse logAuth(Integer idUsuario, String usuarioIngresado, String ip, TipoEventoLogin evento);
    List<LogAuthResponse> getAllLogs();
}
