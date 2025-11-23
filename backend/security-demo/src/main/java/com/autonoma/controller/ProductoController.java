package com.autonoma.controller;

import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.response.ProductoResponse;
import com.autonoma.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productos")
public class ProductoController{

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> getAllProductoes() {
        List<ProductoResponse> productos = productoService.findAll();

        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> saveProducto(@Valid @RequestBody ProductoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> updateProducto(@PathVariable Integer id,
                                                 @RequestBody @Valid ProductoRequest request) {
        return ResponseEntity.ok(productoService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findPersonalById(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
