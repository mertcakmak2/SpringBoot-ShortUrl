package com.project.urlshortend.service;

import com.project.urlshortend.dto.CreateShortUrlRequestDto;
import com.project.urlshortend.dto.CreateShortUrlResponseDto;
import com.project.urlshortend.model.ShortUrl;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ShortUrlService {

    CreateShortUrlResponseDto createShortUrl(CreateShortUrlRequestDto createShortUrlRequestDto, int userId);
    Boolean isExistShortUrl(String shortUrl);
    List<ShortUrl> findShortUrlsByUserId(int userId);
    ShortUrl findShortUrlByIdAndUserId(int urlId, int userId);
    int deleteShortUrlByIdAndUserId(int urlId, int userId);
    ModelAndView redirectToOriginalUrl(String shortUrl);
}
