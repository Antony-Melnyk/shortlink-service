package com.petproject.shortlink.controller;

import com.petproject.shortlink.controller.web.RedirectController;
import com.petproject.shortlink.exception.LinkNotFoundException;
import com.petproject.shortlink.service.LinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import com.petproject.shortlink.config.TestCacheConfig;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RedirectController.class)
@Import(TestCacheConfig.class)
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LinkService linkService;

    @Test
    void shouldReturn404WhenShortCodeNotFound() throws Exception {
        when(linkService.getOriginalUrlAndIncreaseClickCount("abrakadabra"))
                .thenThrow(new LinkNotFoundException("abrakadabra"));

        mockMvc.perform(get("/abrakadabra"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("status", 404))
                .andExpect(model().attribute(
                        "message",
                        "Link not found with shortCode: abrakadabra"
                ));
    }
}