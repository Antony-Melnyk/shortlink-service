package com.petproject.shortlink.controller;

import com.petproject.shortlink.controller.api.LinkApiController;
import com.petproject.shortlink.service.LinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import com.petproject.shortlink.config.TestCacheConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LinkApiController.class)
@Import(TestCacheConfig.class)
class LinkApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LinkService linkService;

    @Test
    void shouldReturn400WhenUrlIsBlank() throws Exception {
        mockMvc.perform(post("/api/links")
                        .contentType("application/json")
                        .content("""
                                {
                                  "url": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.url").value("URL must not be blank"));
    }
}