package com.ysama.resttemplate.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysama.resttemplate.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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

        log.info(response.getBody());
    }

    @Test
    void testGetPlainJson() throws JsonProcessingException {
        String url = "http://localhost:8080/get-user";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        log.warn(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path("name");
        Assertions.assertNotNull(name.asText());

        log.info(name.asText());
    }

    @Test
    void testGetPojoInsteadOfJson() {
        String url = "http://localhost:8080/get-user";
        User user = restTemplate.getForObject(url, User.class);

        log.info(String.valueOf(user));
    }

    @Test
    void testHeaderContentType() {
        String url = "http://localhost:8080/get-user";
        HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
        Assertions.assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    @Test
    void testPostRequestBody() {
        String url = "http://localhost:8080/add-user";
        HttpEntity<User> request = new HttpEntity<>(new User().setId(1003).setName("Louise"));

        User user = restTemplate.postForObject(url, request, User.class);

        Assertions.assertNotNull(user);
        log.info(user.toString());
    }
    @Test
    void testPostReturnLocation() {
        String url = "http://localhost:8080/add-user-1";
        HttpEntity<User> request = new HttpEntity<>(new User().setId(1003).setName("Louise"));

        URI uri = restTemplate.postForLocation(url, request);
        Assertions.assertNotNull(uri);
        log.info(uri.toString());
    }




}
