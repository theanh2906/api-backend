package com.backend.api.services;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface WebCrawlerService {
    List<String> findImageLinks(String url, String selector, String imageAttribute);

    void downloadImages(Map<String, String> parms, HttpServletResponse response);

    void saveImages(Map<String, String> params);

    List<String> getLinks(String url, Integer size, String selector, String imageSelector, String imageAttribute);
}
