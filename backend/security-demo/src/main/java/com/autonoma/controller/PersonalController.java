package com.autonoma.controller;

import com.autonoma.dto.PersonalRequest;
import com.autonoma.dto.PersonalResponse;
import com.autonoma.service.PersonalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personals")
@RequiredArgsConstructor
@PreAuthorize("denyAll()")
public class PersonalController {

    private final PersonalService personalService;

    @GetMapping
    public ResponseEntity<List<PersonalResponse>> getAllPersonal(){
        return ResponseEntity.ok(personalService.findAll());
    }

    @PostMapping
    public ResponseEntity<PersonalResponse> savePersonal(@RequestBody @Valid PersonalRequest request) {
        return ResponseEntity.ok(personalService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalResponse> updatePersonal(@PathVariable Integer id,
                                                       @RequestBody @Valid PersonalRequest request) {
        return ResponseEntity.ok(personalService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalResponse> findPersonalById(@PathVariable Integer id) {
        return ResponseEntity.ok(personalService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonal(@PathVariable Integer id) {
        personalService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
