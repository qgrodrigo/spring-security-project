package com.autonoma.controller;

import com.autonoma.dto.response.MessageResponse;
import com.autonoma.service.IpUnblockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ip")
@RequiredArgsConstructor
public class IpUnblockController {

    private final IpUnblockService ipUnblockService;

    @PostMapping("/unblock")
    public ResponseEntity<MessageResponse> desbloquearIp(@RequestParam String ip) {
        MessageResponse response = ipUnblockService.desbloquearIp(ip);
        return ResponseEntity.ok(response);
    }
}
