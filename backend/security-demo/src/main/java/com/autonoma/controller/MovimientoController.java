package com.autonoma.controller;

import com.autonoma.dto.request.MovimientoRequest;
import com.autonoma.dto.request.ProductoRequest;
import com.autonoma.dto.response.MovimientoResponse;
import com.autonoma.dto.response.ProductoResponse;
import com.autonoma.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoResponse>> getAllMovimientos(){
        List<MovimientoResponse> response = movimientoService.findAll();

        if (response.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MovimientoResponse> saveMivimiento(@Valid @RequestBody MovimientoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponse> updateProducto(@PathVariable Integer id,
                                                           @RequestBody @Valid MovimientoRequest request) {
        return ResponseEntity.ok(movimientoService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> findPersonalById(@PathVariable Integer id) {
        return ResponseEntity.ok(movimientoService.findById(id));
    }

}
