package com.petproject.shortlink.service;

import com.petproject.shortlink.dto.CreateLinkRequest;
import com.petproject.shortlink.dto.CreateLinkResponse;
import com.petproject.shortlink.entity.LinkEntity;
import com.petproject.shortlink.repository.LinkRepository;
import com.petproject.shortlink.util.ShortCodeGenerator;
import com.petproject.shortlink.exception.LinkNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.petproject.shortlink.dto.LinkStatsResponse;
import com.petproject.shortlink.mapper.LinkMapper;
import com.petproject.shortlink.config.AppProperties;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LinkService {

    private static final Logger log = LoggerFactory.getLogger(LinkService.class);
    private final LinkRepository linkRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final LinkMapper linkMapper;
    private final AppProperties appProperties;
    private final LinkCacheService linkCacheService;

    public LinkService(LinkRepository linkRepository,
                       ShortCodeGenerator shortCodeGenerator,
                       LinkMapper linkMapper,
                       AppProperties appProperties,
                       LinkCacheService linkCacheService
    ) {
        this.linkRepository = linkRepository;
        this.shortCodeGenerator = shortCodeGenerator;
        this.linkMapper = linkMapper;
        this.appProperties = appProperties;
        this.linkCacheService = linkCacheService;
    }

    public CreateLinkResponse createLink(CreateLinkRequest request) {
        log.info("Creating short link for URL: {}", request.getUrl());

        String shortCode = shortCodeGenerator.generate();

        LinkEntity link = new LinkEntity();
        link.setOriginalUrl(request.getUrl());
        link.setShortCode(shortCode);
        link.setCreatedAt(LocalDateTime.now());
        link.setClickCount(0L);

        linkRepository.save(link);

        log.info("Short link created: shortCode={}", link.getShortCode());

        return linkMapper.toCreateLinkResponse(
                link,
                appProperties.getBaseUrl()
        );
    }


    @Transactional
    public String getOriginalUrlAndIncreaseClickCount(String shortCode) {

        log.info("Redirect requested for shortCode={}", shortCode);

        String originalUrl = linkCacheService.getOriginalUrlByShortCode(shortCode);

        linkRepository.incrementClickCountByShortCode(shortCode);

        log.info("Click count incremented for shortCode={}", shortCode);

        return originalUrl;
    }

    public LinkStatsResponse getStats(String shortCode) {

        log.info("Stats requested for shortCode={}", shortCode);

        LinkEntity link = linkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new LinkNotFoundException(shortCode));

        log.info("Stats found for shortCode={}, clickCount={}",
                link.getShortCode(),
                link.getClickCount()
        );
        return linkMapper.toStatsResponse(link);
    }
}