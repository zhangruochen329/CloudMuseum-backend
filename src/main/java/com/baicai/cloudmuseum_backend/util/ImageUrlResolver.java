package com.baicai.cloudmuseum_backend.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlResolver {

    @Value("${app.base-url:}")
    private String baseUrl;
    public String resolve(String imageUrl, HttpServletRequest request) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return imageUrl;
        }
        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
            return imageUrl;
        }
        if (baseUrl != null && !baseUrl.isBlank()) {
            String stripped = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
            return stripped + imageUrl;
        }
        return imageUrl;
    }
}
