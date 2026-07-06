package com.petproject.shortlink.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateLinkRequest {

    @NotBlank(message = "URL must not be blank")
    @URL(message = "URL must be valid")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}