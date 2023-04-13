package com.ysama.config;

import com.ysama.web.HttpComponentsClientHttpRequestFactoryBasicAuth;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory
implements FactoryBean<RestTemplate>, InitializingBean {

    // used for initializing
    private RestTemplate restTemplate;

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public RestTemplate getObject() throws Exception {
        return restTemplate;
    }

    @Override
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HttpHost host = new HttpHost("http","localhost", 8082);

        restTemplate = new RestTemplate(
                new HttpComponentsClientHttpRequestFactoryBasicAuth(host)
        );
    }
}
