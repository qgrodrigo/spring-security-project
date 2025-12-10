package com.autonoma.controller;

import com.autonoma.dto.request.OtpSendRequest;
import com.autonoma.dto.request.OtpVerifyRequest;
import com.autonoma.dto.response.LoginResponse;
import com.autonoma.dto.response.OtpCodeResponse;
import com.autonoma.model.entity.OtpCode;
import com.autonoma.service.AuthService;
import com.autonoma.service.EmailService;
import com.autonoma.service.OtpCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/otp")
public class OtpCodeController {

    private final OtpCodeService otpCodeService;
    private final EmailService emailService;
    private final AuthService authService;


    /**
    // Endpoint para generar y enviar OTP
    @PostMapping("/enviar")
    public OtpCodeResponse sendOtp(@RequestBody OtpVerifyRequest request) {
        OtpCode code = otpCodeService.generateOtp(request.email());
        emailService.sendOtpEmail(request.email(), code.getOtp());
        return new OtpResponseDTO(true, "OTP enviado al correo " + request.email());
    }
    **/

    // Endpoint para verificar OTP
    @PostMapping("/verificar")
    public ResponseEntity<LoginResponse> verifyOtp(@RequestBody OtpVerifyRequest request) {
        LoginResponse response = authService.verifyOtp(request);

        return ResponseEntity.ok(response);
    }

}
