package com.autonoma.service.impl;

import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.request.UpdateProductoRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.ProductoResponse;
import com.autonoma.exception.ResourceNotFoundException;
import com.autonoma.model.entity.Producto;
import com.autonoma.repository.ProductoRepository;
import com.autonoma.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository ProductoRepository;

    @Override
    public MessageResponse save(ProductoRequest request) {

        String message;

        Producto Producto = mapToEntity(request);
        Producto saved = ProductoRepository.save(Producto);

        if (saved == null){
            message = "ERROR al guardar producto";
        }else{
            message = "EXITO: Producto registrado correctamente.";
        }

        MessageResponse messageResponse = new MessageResponse(message);

        return messageResponse;
    }

    @Override
    public MessageResponse update(Integer id, UpdateProductoRequest request) {
        Producto producto = ProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe."));

        producto.setNombre(request.nombre());
        producto.setColor(request.color());
        //producto.setStock(request.stock());
        producto.setTalla(request.talla());
        producto.setPrecio(request.precio());
        producto.setUrlImg(request.urlImg());

        Producto updated = ProductoRepository.save(producto);

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
