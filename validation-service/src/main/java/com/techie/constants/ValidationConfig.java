package com.techie.constants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ValidationConfig {

    @Bean
    public WebClient weBuilder() {
        return WebClient.builder().build();
    }
}
