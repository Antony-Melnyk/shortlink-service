package com.petproject.shortlink.service;

import com.petproject.shortlink.dto.CreateLinkRequest;
import com.petproject.shortlink.dto.CreateLinkResponse;
import com.petproject.shortlink.entity.LinkEntity;
import com.petproject.shortlink.repository.LinkRepository;
import com.petproject.shortlink.util.ShortCodeGenerator;
import org.springframework.stereotype.Service;

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
}