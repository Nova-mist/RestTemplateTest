package com.ysama.service;

import com.ysama.config.RestTemplateFactory;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class LoginService {

    @Autowired
    RestTemplateFactory restTemplateFactory;


    /**
     * Manually Set Authorization HTTP Header
     */
    public void loginMethod_1() throws Exception {
        RestTemplate restTemplate = restTemplateFactory.getObject();


    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }};
    }
}
