package com.autonoma.service.impl;

import com.autonoma.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${recaptcha.secret}")
    private String secret;

    @Override
    public boolean verify(String response) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", secret);
        params.add("response", response);

        ResponseEntity<Map> verifyResponse =
                restTemplate.postForEntity(VERIFY_URL, params, Map.class);

        Map body = verifyResponse.getBody();
        return body != null && (Boolean) body.get("success");
    }
}
