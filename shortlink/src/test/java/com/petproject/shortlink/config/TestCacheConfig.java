package com.petproject.shortlink.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestCacheConfig {

    @Bean
    @Primary
    public CacheManager testCacheManager() {
        return new ConcurrentMapCacheManager("links");
    }
}