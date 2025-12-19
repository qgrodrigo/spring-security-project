package com.autonoma.security;

import com.autonoma.model.enums.TipoEventoLogin;
import com.autonoma.service.LogAuthService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class IpRateLimiter {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    private final LogAuthService logAuthService;

    private Bucket createNewBucket() {
        // Máximo 5 intentos cada 10 minutos
        Bandwidth limit = Bandwidth.classic(15, Refill.intervally(1, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    public boolean tryConsume(String ip, Integer idUsuario, String usuarioIngresado) {
        Bucket bucket = cache.computeIfAbsent(ip, k -> createNewBucket());
        boolean allowed = bucket.tryConsume(1);

        if (!allowed) {
            // Registrar bloqueo en log_auth
            logAuthService.logAuth(
                    idUsuario,
                    usuarioIngresado,
                    ip,
                    TipoEventoLogin.IP_BLOCKED,
                    null,
                    false,
                    null
            );
        }

        return allowed;
    }


    public void desbloquearIp(String ip) {
        cache.remove(ip);
    }

    @Scheduled(fixedRate = 60000) // cada 60 segundos
    public void limpiarCacheAutomatica() {
        cache.clear();
        logAuthService.logAuth(
                null,
                null,
                null,
                TipoEventoLogin.IP_CACHE_CLEARED,
                null,
                false,
                null
        );
    }


}
