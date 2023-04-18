package com.ysama.resttemplate.config;

import com.ysama.resttemplate.entity.User;
import com.ysama.resttemplate.web.CustomClientHttpRequestInterceptor;
import com.ysama.resttemplate.web.UserAgentInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        // RestTemplate restTemplate = new RestTemplate();
        RestTemplate restTemplate
                = new RestTemplate(
                new BufferingClientHttpRequestFactory(
                        new SimpleClientHttpRequestFactory()
                )
        );

        List<ClientHttpRequestInterceptor> interceptors
                = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new CustomClientHttpRequestInterceptor());
        interceptors.add(new UserAgentInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }


    // build RestTemplate method 5

    // @Bean
    // public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
    //     RestTemplate restTemplate = new RestTemplate(factory);
    //     List<ClientHttpRequestInterceptor> interceptors
    //             = restTemplate.getInterceptors();
    //     if (CollectionUtils.isEmpty(interceptors)) {
    //         interceptors = new ArrayList<>();
    //     }
    //     interceptors.add(new CustomClientHttpRequestInterceptor());
    //     interceptors.add(new UserAgentInterceptor());
    //     restTemplate.setInterceptors(interceptors);
    //     return restTemplate;
    // }
    //
    // @Bean
    // public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
    //     SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    //     factory.setReadTimeout(5000);
    //     factory.setConnectTimeout(15000);
    //     // factory.setProxy(null);
    //     return factory;
    // }
}
