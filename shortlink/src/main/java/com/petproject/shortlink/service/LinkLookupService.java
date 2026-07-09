package com.petproject.shortlink.service;

import com.petproject.shortlink.entity.LinkEntity;
import com.petproject.shortlink.exception.LinkNotFoundException;
import com.petproject.shortlink.repository.LinkRepository;
import org.springframework.stereotype.Service;

@Service
public class LinkLookupService {

    private final LinkRepository linkRepository;

    public LinkLookupService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String getOriginalUrlByShortCode(String shortCode) {
        LinkEntity link = linkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new LinkNotFoundException(shortCode));

        return link.getOriginalUrl();
    }
}