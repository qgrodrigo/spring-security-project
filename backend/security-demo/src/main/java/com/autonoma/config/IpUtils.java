package com.autonoma.config;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtils {

    private IpUtils() {
        // Evitar instanciación
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
