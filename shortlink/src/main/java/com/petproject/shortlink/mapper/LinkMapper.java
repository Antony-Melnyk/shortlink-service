package com.petproject.shortlink.mapper;

import com.petproject.shortlink.dto.CreateLinkResponse;
import com.petproject.shortlink.dto.LinkStatsResponse;
import com.petproject.shortlink.entity.LinkEntity;
import org.springframework.stereotype.Component;

/// так вот же маппер)
@Component
public class LinkMapper {

    public CreateLinkResponse toCreateLinkResponse(LinkEntity link, String baseUrl) {
        CreateLinkResponse response = new CreateLinkResponse();
        response.setShortCode(link.getShortCode());
        response.setShortUrl(baseUrl + "/" + link.getShortCode());
        return response;
    }

    public LinkStatsResponse toStatsResponse(LinkEntity link) {
        LinkStatsResponse response = new LinkStatsResponse();
        response.setOriginalUrl(link.getOriginalUrl());
        response.setShortCode(link.getShortCode());
        response.setClickCount(link.getClickCount());
        response.setCreatedAt(link.getCreatedAt());
        return response;
    }
}