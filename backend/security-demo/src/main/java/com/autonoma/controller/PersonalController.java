package com.autonoma.controller;

import com.autonoma.dto.request.PersonalRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.PersonalResponse;
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
public class PersonalController {

    private final PersonalService personalService;

    @GetMapping
    public ResponseEntity<List<PersonalResponse>> getAllPersonal(){
        return ResponseEntity.ok(personalService.findAll());
    }
    @PostMapping
    public ResponseEntity<MessageResponse> savePersonal(@RequestBody @Valid PersonalRequest request) {
        MessageResponse message = personalService.save(request);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updatePersonal(@PathVariable Integer id,
                                                       @RequestBody @Valid PersonalRequest request) {
        MessageResponse message = personalService.update(id, request);
        return ResponseEntity.ok(message);
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
