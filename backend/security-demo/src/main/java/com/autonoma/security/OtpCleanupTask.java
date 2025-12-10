package com.autonoma.security;

import com.autonoma.repository.OtpCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OtpCleanupTask {

    private final OtpCodeRepository otpCodeRepository;

    @Scheduled(fixedRate = 60000)
    public void cleanExpiredOtps() {
        otpCodeRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        System.out.println("OTP expirados eliminados: " + LocalDateTime.now());
    }
}
