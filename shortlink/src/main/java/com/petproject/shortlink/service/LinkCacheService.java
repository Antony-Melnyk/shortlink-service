package com.petproject.shortlink.service;

import com.petproject.shortlink.entity.LinkEntity;
import com.petproject.shortlink.exception.LinkNotFoundException;
import com.petproject.shortlink.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LinkCacheService {

    private static final Logger log = LoggerFactory.getLogger(LinkCacheService.class);

    private final LinkRepository linkRepository;

    public LinkCacheService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Cacheable(value = "links", key = "#shortCode")
    public String getOriginalUrlByShortCode(String shortCode) {
        log.info("Loading original URL from database for shortCode={}", shortCode);

        LinkEntity link = linkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new LinkNotFoundException(shortCode));

        return link.getOriginalUrl();
    }
}