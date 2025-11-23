package com.autonoma.service.impl;

import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.response.ProductoResponse;
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
    public ProductoResponse save(ProductoRequest request) {
        Producto Producto = mapToEntity(request);
        Producto saved = ProductoRepository.save(Producto);
        return mapToResponse(saved);
    }

    @Override
    public ProductoResponse update(Integer id, ProductoRequest request) {
        Producto Producto = ProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Producto.setNombre(request.nombre());
        Producto updated = ProductoRepository.save(Producto);
        return mapToResponse(updated);
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
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
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
                producto.getFechaCreacion().format(formatter),
                producto.getUrlImg()
        );
    }

}
