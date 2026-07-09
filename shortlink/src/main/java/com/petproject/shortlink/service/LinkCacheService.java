package com.petproject.shortlink.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LinkCacheService {

    private static final Logger log = LoggerFactory.getLogger(LinkCacheService.class);

    private final LinkLookupService linkLookupService;

    public LinkCacheService(LinkLookupService linkLookupService) {
        this.linkLookupService = linkLookupService;
    }

    @Cacheable(value = "links", key = "#shortCode")
    public String getOriginalUrlByShortCode(String shortCode) {
        log.info("Loading original URL through cache layer for shortCode={}", shortCode);

        return linkLookupService.getOriginalUrlByShortCode(shortCode);
    }
}