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


}
