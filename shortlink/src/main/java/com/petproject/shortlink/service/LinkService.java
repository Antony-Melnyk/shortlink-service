package com.petproject.shortlink.service;

import com.petproject.shortlink.repository.LinkRepository;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }
}