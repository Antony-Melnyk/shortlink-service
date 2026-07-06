package com.petproject.shortlink.service;

import com.petproject.shortlink.dto.CreateLinkRequest;
import com.petproject.shortlink.dto.CreateLinkResponse;
import com.petproject.shortlink.entity.LinkEntity;
import com.petproject.shortlink.repository.LinkRepository;
import com.petproject.shortlink.util.ShortCodeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.petproject.shortlink.mapper.LinkMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    @Mock
    private LinkMapper linkMapper;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private ShortCodeGenerator shortCodeGenerator;

    @InjectMocks
    private LinkService linkService;

    @Test
    void shouldCreateShortLink() {
        CreateLinkRequest request = new CreateLinkRequest();
        request.setUrl("https://youtube.com");

        when(shortCodeGenerator.generate()).thenReturn("abc123");

        CreateLinkResponse mappedResponse = new CreateLinkResponse();
        mappedResponse.setShortCode("abc123");
        mappedResponse.setShortUrl("http://localhost:8080/abc123");

        when(linkMapper.toCreateLinkResponse(any(LinkEntity.class), eq("http://localhost:8080")))
                .thenReturn(mappedResponse);

        CreateLinkResponse response = linkService.createLink(request);

        assertEquals("abc123", response.getShortCode());
        assertEquals("http://localhost:8080/abc123", response.getShortUrl());

        ArgumentCaptor<LinkEntity> captor = ArgumentCaptor.forClass(LinkEntity.class);

        verify(linkRepository).save(captor.capture());

        LinkEntity savedLink = captor.getValue();

        assertEquals("https://youtube.com", savedLink.getOriginalUrl());
        assertEquals("abc123", savedLink.getShortCode());
        assertEquals(0L, savedLink.getClickCount());
    }
}