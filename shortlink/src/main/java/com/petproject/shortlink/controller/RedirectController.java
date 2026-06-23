package com.petproject.shortlink.controller;

import com.petproject.shortlink.service.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedirectController {

    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode) {

        String originalUrl =
                linkService.getOriginalUrlAndIncreaseClickCount(shortCode);

        return ResponseEntity.status(302)
                .header("Location", originalUrl)
                .build();
    }
}