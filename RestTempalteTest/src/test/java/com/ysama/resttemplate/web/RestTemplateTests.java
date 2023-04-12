package com.ysama.resttemplate.web;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Slf4j
public class RestTemplateTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void testGetString() {

        String url = "http://localhost:8080/hello";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        log.warn(response.getBody());
    }

}
