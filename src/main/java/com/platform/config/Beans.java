package com.platform.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Hchier
 * @Date 2022/5/25 22:45
 */
@Configuration
public class Beans {
    @Bean("ObjectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
