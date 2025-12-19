package com.autonoma.service;

import com.autonoma.dto.response.MessageResponse;

public interface IpUnblockService {

    MessageResponse desbloquearIp(String ip);
}
