package com.petproject.shortlink.controller.api;

import com.petproject.shortlink.dto.CreateLinkRequest;
import com.petproject.shortlink.dto.CreateLinkResponse;
import com.petproject.shortlink.service.LinkService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.petproject.shortlink.dto.LinkStatsResponse;

@RestController
@RequestMapping("/api/links")
public class LinkApiController {

    private final LinkService linkService;

    public LinkApiController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public CreateLinkResponse createLink(@Valid @RequestBody CreateLinkRequest request) {
        return linkService.createLink(request);
    }

    @GetMapping("/{shortCode}/stats")
    public LinkStatsResponse getStats(@PathVariable String shortCode) {
        return linkService.getStats(shortCode);
    }
}