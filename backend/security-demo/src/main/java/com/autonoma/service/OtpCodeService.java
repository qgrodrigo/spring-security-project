package com.autonoma.service;

import com.autonoma.model.entity.OtpCode;

public interface OtpCodeService {

    OtpCode generateOtp(Integer userId);
    boolean validateOtp(Integer userId, String otp);
}
