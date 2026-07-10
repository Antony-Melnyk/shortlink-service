package com.petproject.shortlink.controller.web;

import com.petproject.shortlink.dto.CreateLinkRequest;
import com.petproject.shortlink.dto.CreateLinkResponse;
import com.petproject.shortlink.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LinkPageController {

    private final LinkService linkService;

    public LinkPageController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("request", new CreateLinkRequest());
        return "index";
    }

    @PostMapping("/links")
    public String createLink(@Valid CreateLinkRequest request, Model model) {
        CreateLinkResponse response = linkService.createLink(request);

        model.addAttribute("request", new CreateLinkRequest());
        model.addAttribute("shortUrl", response.getShortUrl());

        return "index";
    }
}