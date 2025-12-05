package com.autonoma.service.impl;

import com.autonoma.dto.response.LogProductoResponse;
import com.autonoma.exception.ResourceNotFoundException;
import com.autonoma.model.entity.LogProducto;
import com.autonoma.model.entity.Producto;
import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.TipoEventoProducto;
import com.autonoma.repository.LogProductoRepository;
import com.autonoma.repository.ProductoRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.service.LogProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogProductoServiceImpl implements LogProductoService {

    private final LogProductoRepository logProductoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public LogProductoResponse logProducto(Integer idUsuario, String ip, TipoEventoProducto evento, Integer idProducto) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        LogProducto log = new LogProducto();
        log.setFecha(LocalDate.now());
        log.setHora(LocalTime.now());
        log.setUsuario(usuario);
        log.setProducto(producto);
        log.setIpAddress(ip);
        log.setTipoEvento(evento);

        LogProducto saved = logProductoRepository.save(log);

        return new LogProductoResponse(
                saved.getId(),
                saved.getFecha(),
                saved.getHora(),
                saved.getUsuario().getId(),
                saved.getIpAddress(),
                saved.getTipoEvento().name(),
                saved.getProducto().getId(),
                saved.getProducto().getNombre()
        );
    }

    @Override
    public List<LogProductoResponse> getAllLogs() {
        return logProductoRepository.findAll().stream()
                .map(log -> new LogProductoResponse(
                        log.getId(),
                        log.getFecha(),
                        log.getHora(),
                        log.getUsuario().getId(),
                        log.getIpAddress(),
                        log.getTipoEvento().name(),
                        log.getProducto().getId(),
                        log.getProducto().getNombre()
                ))
                .toList();
    }
}
