package com.autonoma.service.impl;

import com.autonoma.model.entity.OtpCode;
import com.autonoma.repository.OtpCodeRepository;
import com.autonoma.security.OtpGenerator;
import com.autonoma.service.OtpCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpCodeServiceImpl implements OtpCodeService {

    private final OtpCodeRepository otpCodeRepository;


    @Override
    public OtpCode generateOtp(Integer userId) {



        otpCodeRepository.findByUserIdAndUsedFalse(userId)
                .forEach(code -> {
                    code.setUsed(true);
                    otpCodeRepository.save(code);
                });

        String otp = OtpGenerator.generateOtp();
        OtpCode code = new OtpCode();
        code.setUserId(userId);
        code.setOtp(otp);
        code.setExpiresAt(LocalDateTime.now().plusMinutes(3)); // expira en 5 min
        code.setUsed(false);
        return otpCodeRepository.save(code);
    }

    @Override
    public boolean validateOtp(Integer userId, String otp) {
        return otpCodeRepository.findByUserIdAndOtpAndUsedFalse(userId, otp)
                .filter(code -> code.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(code -> {
                    code.setUsed(true); // marcar como usado
                    otpCodeRepository.save(code);
                    return true;
                })
                .orElse(false);
    }
}
