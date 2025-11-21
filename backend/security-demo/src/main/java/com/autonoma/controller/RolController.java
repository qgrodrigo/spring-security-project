package com.autonoma.controller;


import com.autonoma.dto.request.RolRequest;
import com.autonoma.dto.response.RolResponse;
import com.autonoma.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolResponse>> getAllRoles() {
        return ResponseEntity.ok(rolService.findAll());
    }

    @PostMapping
    public ResponseEntity<RolResponse> saveRol(@RequestBody @Valid RolRequest request) {
        return ResponseEntity.ok(rolService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponse> updateRol(@PathVariable Integer id,
                                                 @RequestBody @Valid RolRequest request) {
        return ResponseEntity.ok(rolService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponse> findPersonalById(@PathVariable Integer id) {
        return ResponseEntity.ok(rolService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
