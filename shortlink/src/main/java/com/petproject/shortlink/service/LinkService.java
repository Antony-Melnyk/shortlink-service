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

import java.time.LocalDateTime;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    public LinkService(LinkRepository linkRepository, ShortCodeGenerator shortCodeGenerator) {
        this.linkRepository = linkRepository;
        this.shortCodeGenerator = shortCodeGenerator;
    }

    public CreateLinkResponse createLink(CreateLinkRequest request) {
        String shortCode = shortCodeGenerator.generate();

        LinkEntity link = new LinkEntity();
        link.setOriginalUrl(request.getUrl());
        link.setShortCode(shortCode);
        link.setCreatedAt(LocalDateTime.now());
        link.setClickCount(0L);

        linkRepository.save(link);

        CreateLinkResponse response = new CreateLinkResponse();
        response.setShortCode(shortCode);
        response.setShortUrl("http://localhost:8080/" + shortCode);

        return response;
    }

    @Transactional
    public String getOriginalUrlAndIncreaseClickCount(String shortCode) {
        LinkEntity link = linkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new LinkNotFoundException(shortCode));

        linkRepository.incrementClickCountByShortCode(shortCode);

        return link.getOriginalUrl();
    }

    public LinkStatsResponse getStats(String shortCode) {
        LinkEntity link = linkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new LinkNotFoundException(shortCode));

        LinkStatsResponse response = new LinkStatsResponse();
        response.setOriginalUrl(link.getOriginalUrl());
        response.setShortCode(link.getShortCode());
        response.setClickCount(link.getClickCount());
        response.setCreatedAt(link.getCreatedAt());

        return response;
    }
}