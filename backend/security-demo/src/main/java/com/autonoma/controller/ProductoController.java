package com.autonoma.controller;

import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.response.ProductoResponse;
import com.autonoma.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/productos")
public class ProductoController{

    private final ProductoService ProductoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> getAllProductoes() {
        return ResponseEntity.ok(ProductoService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> saveProducto(@RequestBody @Valid ProductoRequest request) {
        return ResponseEntity.ok(ProductoService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> updateProducto(@PathVariable Integer id,
                                                 @RequestBody @Valid ProductoRequest request) {
        return ResponseEntity.ok(ProductoService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findPersonalById(@PathVariable Integer id) {
        return ResponseEntity.ok(ProductoService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        ProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
