package com.autonoma.service;

import com.autonoma.dto.response.LogProductoResponse;
import com.autonoma.model.enums.TipoEventoProducto;

import java.util.List;

public interface LogProductoService {

    LogProductoResponse logProducto(Integer idUsuario, String ip, TipoEventoProducto evento, Integer idProducto);
    List<LogProductoResponse> getAllLogs();

}
