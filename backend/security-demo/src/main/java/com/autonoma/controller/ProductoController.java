package com.autonoma.controller;

import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.request.UpdateProductoRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.ProductoResponse;
import com.autonoma.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<MessageResponse> saveProducto(@Valid @RequestBody ProductoRequest request,
                                                        HttpServletRequest httpRequest) {

        MessageResponse message = productoService.save(request, httpRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateProducto(@PathVariable Integer id,
                                                         @RequestBody @Valid UpdateProductoRequest request,
                                                          HttpServletRequest httpRequest) {

        MessageResponse message = productoService.update(id, request, httpRequest);
        return ResponseEntity.ok(message);
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
