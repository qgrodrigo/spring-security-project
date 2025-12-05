package com.autonoma.controller;

import com.autonoma.dto.response.LogProductoResponse;
import com.autonoma.service.LogProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/logs/producto")
public class LogProductoController {

    private final LogProductoService logProductoService;

    @GetMapping
    public ResponseEntity<List<LogProductoResponse>> getAllLogs() {
        return ResponseEntity.ok(logProductoService.getAllLogs());
    }
}
