package com.ysama.resttemplate.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        logRequestDetails(request);
        ClientHttpResponse response = execution.execute(request, body);

        log.info("Response : {}", new String(response.getBody().readAllBytes()));
        return response;
    }
    private void logRequestDetails(HttpRequest request) {
        log.info("Headers: {}", request.getHeaders());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request URI: {}", request.getURI());
    }
}
