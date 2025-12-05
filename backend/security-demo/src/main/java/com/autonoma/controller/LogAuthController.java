package com.autonoma.controller;

import com.autonoma.dto.response.LogAuthResponse;
import com.autonoma.service.LogAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/logs/auth")
@RequiredArgsConstructor
public class LogAuthController {

    private final LogAuthService logAuthService;

    @GetMapping
    public ResponseEntity<List<LogAuthResponse>> getAllAuthLogs() {
        return ResponseEntity.ok(logAuthService.getAllLogs());
    }
}
