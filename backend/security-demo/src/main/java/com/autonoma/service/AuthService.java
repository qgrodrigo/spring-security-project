package com.autonoma.service;

import com.autonoma.dto.request.LoginRequest;
import com.autonoma.dto.request.OtpVerifyRequest;
import com.autonoma.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request, HttpServletRequest httpRequest);
    LoginResponse verifyOtp(OtpVerifyRequest request, HttpServletRequest httpRequest);

    void logout(HttpServletRequest httpRequest);
}
