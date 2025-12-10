package com.autonoma.security;

import java.security.SecureRandom;

public class OtpGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generateOtp() {
        int number = random.nextInt(999999);
        return String.format("%06d", number); // 6 dígitos
    }
}
