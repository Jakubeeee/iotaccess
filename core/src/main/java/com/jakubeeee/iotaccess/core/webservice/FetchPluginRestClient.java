package com.jakubeeee.iotaccess.core.webservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class FetchPluginRestClient {

    private final RestTemplate restTemplate;

    FetchPluginRestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String fetchData(String url) {
        return restTemplate.getForObject(url, String.class);
    }

}
