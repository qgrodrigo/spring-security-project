package com.autonoma.service.impl;

import com.autonoma.dto.response.MessageResponse;
import com.autonoma.model.enums.TipoEventoLogin;
import com.autonoma.security.IpRateLimiter;
import com.autonoma.service.IpUnblockService;
import com.autonoma.service.LogAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IpUnblockServiceImpl implements IpUnblockService {

    private final IpRateLimiter ipRateLimiter;
    private final LogAuthService logAuthService;


    @Override
    public MessageResponse desbloquearIp(String ip) {

        ipRateLimiter.desbloquearIp(ip);

        logAuthService.logAuth(
                null,
                null,
                ip,
                TipoEventoLogin.IP_UNBLOCKED,
                null,
                false,
                null
        );

        return new MessageResponse("EXITO: IP " + ip + " desbloqueada.");
    }

}
