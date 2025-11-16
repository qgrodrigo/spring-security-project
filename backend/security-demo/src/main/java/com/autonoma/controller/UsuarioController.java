package com.autonoma.controller;

import com.autonoma.dto.UsuarioRequest;
import com.autonoma.dto.UsuarioResponse;
import com.autonoma.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
@PreAuthorize("denyAll()")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public ResponseEntity<UsuarioResponse> saveUsuario(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(@PathVariable Integer id,
                                                         @RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.update(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> findUsuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reset")
    public ResponseEntity<UsuarioResponse> restablecerContraseña(@PathVariable Integer id){
        UsuarioResponse response = usuarioService.restablecerContraseña(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<UsuarioResponse> deactivateUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.desactivarUsuario(id));
    }

}
