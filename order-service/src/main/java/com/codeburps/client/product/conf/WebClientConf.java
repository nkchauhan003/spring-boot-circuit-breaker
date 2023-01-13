package com.codeburps.client.product.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebClientConf {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
