package com.autonoma.service.impl;

import com.autonoma.dto.request.MovimientoRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.MovimientoResponse;
import com.autonoma.exception.ResourceNotFoundException;
import com.autonoma.model.entity.Movimiento;
import com.autonoma.model.entity.Producto;
import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.TipoMovimiento;
import com.autonoma.repository.MovimientoRepository;
import com.autonoma.repository.ProductoRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public MessageResponse save(MovimientoRequest request) {

        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe."));

        switch (request.tipoMovimiento()) {
            case ENTRADA -> producto.setStock(producto.getStock() + request.cantidad());

            case SALIDA -> {
                if (request.cantidad() > producto.getStock()) {
                    return new MessageResponse("La cantidad de salida no puede exceder el stock actual ("
                            + producto.getStock() + ")");
                }
                producto.setStock(producto.getStock() - request.cantidad());
            }
        }

        productoRepository.save(producto);

        Movimiento movimiento = mapToEntity(request);
        movimientoRepository.save(movimiento);

        return new MessageResponse("Movimiento registrado correctamente.");
    }

    @Override
    public MessageResponse update(Integer id, MovimientoRequest request) {

        String message = "";

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no existe."));

        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe."));


        if (request.tipoMovimiento() == TipoMovimiento.ENTRADA) {
            producto.setStock(producto.getStock() + request.cantidad());
        } else {
            if (request.cantidad() > producto.getStock())
            {
                message = "La cantidad de salida no puede exceder del actual";

            }else{
                producto.setStock(producto.getStock() - request.cantidad());
            }

        }

        movimiento.setUsuario(usuario);
        movimiento.setProducto(producto);
        movimiento.setCantidad(request.cantidad());
        movimiento.setTipoMovimiento(request.tipoMovimiento());

        Movimiento updated = movimientoRepository.save(movimiento);


        if (updated != null){
            message = "Movimiento actualizado correctamente";
        }


        return new MessageResponse(message);
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
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no existe."));
        return mapToResponse(movimiento);
    }

    @Override
    public void delete(Integer id) {

    }

    private Movimiento mapToEntity(MovimientoRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));// o findByUsername

        //Usuario usuario = usuarioRepository.findById(request.idUsuario())
        //        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe."));

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
