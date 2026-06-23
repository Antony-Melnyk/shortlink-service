package com.petproject.shortlink.controller;

import com.petproject.shortlink.dto.CreateLinkRequest;
import com.petproject.shortlink.dto.CreateLinkResponse;
import com.petproject.shortlink.service.LinkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/links") // later RedirectController (/api/links)
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public CreateLinkResponse createLink(@RequestBody CreateLinkRequest request) {
        return linkService.createLink(request);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = linkService.getOriginalUrlAndIncreaseClickCount(shortCode);

        return ResponseEntity.status(302)
                .header("Location", originalUrl)
                .build();
    }
}