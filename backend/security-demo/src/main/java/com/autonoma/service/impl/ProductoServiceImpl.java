package com.autonoma.service.impl;

import com.autonoma.config.IpUtils;
import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.request.UpdateProductoRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.ProductoResponse;
import com.autonoma.exception.ResourceNotFoundException;
import com.autonoma.model.entity.Producto;
import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.TipoEventoProducto;
import com.autonoma.repository.ProductoRepository;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.service.LogProductoService;
import com.autonoma.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository ProductoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LogProductoService logProductoService;

    @Override
    public MessageResponse save(ProductoRequest request, HttpServletRequest httpRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));

        String message = "";

        Producto producto = mapToEntity(request);
        Producto saved = ProductoRepository.save(producto);

        String ip = IpUtils.getClientIp(httpRequest);


        if (saved == null){
            message = "ERROR al guardar producto";
        }else{
            message = "EXITO: Producto registrado correctamente.";
            logProductoService.logProducto(usuario.getId(), ip, TipoEventoProducto.PRODUCT_CREATE, producto.getId());
        }



        MessageResponse messageResponse = new MessageResponse(message);

        return messageResponse;
    }

    @Override
    public MessageResponse update(Integer id, UpdateProductoRequest request, HttpServletRequest httpRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no existe"));

        Producto producto = ProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe."));

        producto.setNombre(request.nombre());
        producto.setColor(request.color());
        //producto.setStock(request.stock());
        producto.setTalla(request.talla());
        producto.setPrecio(request.precio());
        producto.setUrlImg(request.urlImg());

        Producto updated = ProductoRepository.save(producto);

        String ip = IpUtils.getClientIp(httpRequest);
        logProductoService.logProducto(usuario.getId(), ip, TipoEventoProducto.PRODUCT_UPDATE, producto.getId());

        MessageResponse messageResponse = new MessageResponse("EXITO: Producto actualizado correctamente.");

        return messageResponse;
    }

    @Override
    public List<ProductoResponse> findAll() {
        return ProductoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductoResponse findById(Integer id) {
        Producto Producto = ProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe."));

        return mapToResponse(Producto);
    }

    @Override
    public void delete(Integer id) {
        ProductoRepository.deleteById(id);
    }

    private Producto mapToEntity(ProductoRequest request) {
        Producto producto = new Producto();

        producto.setNombre(request.nombre());
        producto.setStock(request.stock());
        producto.setColor(request.color());
        producto.setTalla(request.talla());
        producto.setPrecio(request.precio());
        producto.setUrlImg(request.urlImg());

        return producto;
    }

    private ProductoResponse mapToResponse(Producto producto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getStock(),
                producto.getColor(),
                producto.getTalla(),
                producto.getPrecio(),
                producto.getFechaCreacion().format(formatter),
                producto.getUrlImg()
        );
    }

}
