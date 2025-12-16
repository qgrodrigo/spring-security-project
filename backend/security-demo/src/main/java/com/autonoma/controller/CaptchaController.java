package com.autonoma.controller;

import com.autonoma.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/captcha")
public class CaptchaController {
    private final CaptchaService captchaService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam("g-recaptcha-response") String captchaResponse) {

        if (!captchaService.verify(captchaResponse)) {
            return ResponseEntity.badRequest().body("Captcha inválido");
        }

        // Aquí continúa tu lógica de registro
        return ResponseEntity.ok("Registro exitoso");
    }
}
