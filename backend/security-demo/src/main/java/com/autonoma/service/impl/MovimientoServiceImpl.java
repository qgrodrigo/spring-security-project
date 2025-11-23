package com.autonoma.service.impl;

import com.autonoma.dto.request.MovimientoRequest;
import com.autonoma.dto.response.MovimientoResponse;
import com.autonoma.model.entity.Movimiento;
import com.autonoma.model.entity.Producto;
import com.autonoma.model.entity.Usuario;
import com.autonoma.repository.MovimientoRepository;
import com.autonoma.repository.ProductoRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public MovimientoResponse save(MovimientoRequest request) {
        Movimiento movimiento = mapToEntity(request);
        Movimiento saved = movimientoRepository.save(movimiento);
        return mapToResponse(saved);
    }

    @Override
    public MovimientoResponse update(Integer id, MovimientoRequest request) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        movimiento.setUsuario(usuario);
        movimiento.setProducto(producto);
        movimiento.setCantidad(request.cantidad());
        movimiento.setTipoMovimiento(request.tipoMovimiento());

        Movimiento updated = movimientoRepository.save(movimiento);

        return mapToResponse(updated);
    }

    @Override
    public List<MovimientoResponse> findAll() {
        return movimientoRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MovimientoResponse findById(Integer id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        return mapToResponse(movimiento);
    }

    @Override
    public void delete(Integer id) {

    }

    private Movimiento mapToEntity(MovimientoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Movimiento movimiento = new Movimiento();
        movimiento.setUsuario(usuario);
        movimiento.setProducto(producto);
        movimiento.setCantidad(request.cantidad());
        movimiento.setTipoMovimiento(request.tipoMovimiento());

        return movimiento;
    }

    private MovimientoResponse mapToResponse(Movimiento movimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new MovimientoResponse(
                movimiento.getId(),
                movimiento.getUsuario().getId(),
                movimiento.getProducto().getId(),
                movimiento.getCantidad(),
                movimiento.getFechaMovimiento().format(formatter),
                movimiento.getTipoMovimiento().name()
        );
    }
}
