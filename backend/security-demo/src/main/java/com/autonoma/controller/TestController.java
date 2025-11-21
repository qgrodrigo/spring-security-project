package com.autonoma.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("denyAll()")
public class TestController {


    @GetMapping("test")
    @PreAuthorize("permitAll()")
    public String sayHello(){
        return "Hello world... security demo";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/auth-secure")
    public String authTest(){
        return "Auth: exit...";
    }

}
