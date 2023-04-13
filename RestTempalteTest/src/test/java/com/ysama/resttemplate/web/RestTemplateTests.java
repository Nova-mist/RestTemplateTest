package com.ysama.resttemplate.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysama.resttemplate.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

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
        Assertions.assertTrue(Objects.requireNonNull(httpHeaders.getContentType()).includes(MediaType.APPLICATION_JSON));
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

    @Test
    void testExchange() {
        String url = "http://localhost:8080/add-user";
        HttpEntity<User> request = new HttpEntity<>(new User().setId(1003).setName("Louise"));
        // return ResponseEntity not Object or URI, more generic
        ResponseEntity<User> response = restTemplate
                .exchange(url, HttpMethod.POST, request, User.class);
        User user = response.getBody();

        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getName(), "Louise");
    }

    @Test
    void testSubmit() {
        String url = "http://localhost:8080/login";
        // 1.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 2.
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "ysama");
        map.add("password", "123456");
        // 3.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        // 4.
        ResponseEntity<String> response = restTemplate.postForEntity(
                url, request, String.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        log.info(response.toString());
    }

    @Test
    void testSimplePutWithExchange() {
        String url = "http://localhost:8080/update-user";
        HttpEntity<User> request = new HttpEntity<>(new User().setId(1003).setName("Louise"));
        // no ResponseBody
        restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
    }

    @Test
    void testPutWithCallBack() {
        String url = "http://localhost:8080/update-user";
        User updatedUser = new User().setId(1003).setName("Louise");
        restTemplate.execute(url,
                HttpMethod.PUT,
                requestCallback(updatedUser),
                clientHttpResponse -> null);
    }

    RequestCallback requestCallback(final User updatedUser) {
        return clientHttpRequest -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), updatedUser);
            clientHttpRequest.getHeaders().add(
                    HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
            );
            clientHttpRequest.getHeaders().add(
                    HttpHeaders.AUTHORIZATION, "Basic " + getBase64EncodedLogPass()
            );
        };
    }
    private String getBase64EncodedLogPass() {
        final String logPass = "user1:user1Pass";
        final byte[] authHeaderBytes = encodeBase64(logPass.getBytes(StandardCharsets.ISO_8859_1));
        return new String(authHeaderBytes, StandardCharsets.ISO_8859_1);
    }

    @Test
    void testDelete() {
        String url = "http://localhost:8080/delete-user";

        // no response
        restTemplate.delete(url);
    }


}
