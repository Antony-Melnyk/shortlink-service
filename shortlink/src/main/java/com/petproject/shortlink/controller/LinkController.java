package com.petproject.shortlink.controller;

import org.springframework.web.bind.annotation.RestController;
import com.petproject.shortlink.dto.CreateLinkRequest;
import com.petproject.shortlink.dto.CreateLinkResponse;
import org.springframework.web.bind.annotation.*; //????

@RestController
@RequestMapping("/api/links")
public class LinkController {

    @PostMapping
    public CreateLinkResponse createLink(@RequestBody CreateLinkRequest request) {
        CreateLinkResponse response = new CreateLinkResponse();
        response.setShortCode("test123");
        response.setShortUrl("http://localhost:8080/test123");
        return response;
    }
}