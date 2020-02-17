package com.jakubeeee.masterthesis.core.webservice;

import com.jakubeeee.masterthesis.pluginapi.config.FetchConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service bean used for rest communication using configuration specified by plugins.
 */
@Slf4j
@Service
public class FetchPluginRestClient {

    private final RestTemplate restTemplate;

    FetchPluginRestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String fetchData(FetchConfig config) {
        LOG.debug("Fetching data from \"{}\"", config.getUrl());
        return restTemplate.getForObject(config.getUrl(), String.class);
    }

}
