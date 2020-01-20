package org.sng.shortener.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class ConfigurationService {

    @Value("${shortener.service.url}")
    private String serviceUrl;


    public String getServiceUrl() {
        return serviceUrl;
    }
}
