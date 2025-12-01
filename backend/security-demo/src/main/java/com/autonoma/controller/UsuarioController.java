package com.autonoma.controller;

import com.autonoma.dto.request.UpdateRolRequest;
import com.autonoma.dto.request.UsuarioRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.UserResponse;
import com.autonoma.dto.response.UsuarioResponse;
import com.autonoma.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

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
    public ResponseEntity<UsuarioResponse> findUsuarioById(@PathVariable @NotNull @Positive Integer id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reset")
    public ResponseEntity<UsuarioResponse> restablecerContraseña(@PathVariable @NotNull @Positive Integer id){
        UsuarioResponse response = usuarioService.restablecerContraseña(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<MessageResponse> activateUserById(@PathVariable @NotNull @Positive Integer id) {

        MessageResponse messageResponse = usuarioService.activarUsuario(id);

        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<UserResponse> mostrarUserPassword(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.showUsuario(id));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<MessageResponse> deactivateUserById(@PathVariable Integer id) {
        
        return ResponseEntity.ok(usuarioService.desactivarUsuario(id));
    }

    @PatchMapping("/{id}/rol")
    public ResponseEntity<MessageResponse> cambiarRolUsuario(@PathVariable Integer id,
                                                                @RequestBody @Valid UpdateRolRequest request) {

        MessageResponse message = usuarioService.cambiarRol(id, request);
        return ResponseEntity.ok(message);
    }

}
