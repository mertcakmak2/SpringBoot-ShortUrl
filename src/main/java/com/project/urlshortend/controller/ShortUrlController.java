package com.project.urlshortend.controller;

import com.project.urlshortend.dto.CreateShortUrlRequestDto;
import com.project.urlshortend.dto.CreateShortUrlResponseDto;
import com.project.urlshortend.model.ShortUrl;
import com.project.urlshortend.service.ShortUrlService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping("user/{userId}/url/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "")
    public CreateShortUrlResponseDto createShortUrl(
            @RequestBody CreateShortUrlRequestDto createShortUrlRequestDto, @PathVariable int userId){
        return shortUrlService.createShortUrl(createShortUrlRequestDto, userId);
    }

    @GetMapping("user/{userId}/url/list")
    @ApiOperation(value = "")
    public List<ShortUrl> findShortUrlsByUserId(@PathVariable int userId) {
        return shortUrlService.findShortUrlsByUserId(userId);
    }

    @GetMapping("user/{userId}/url/detail/{urlId}")
    @ApiOperation(value = "")
    public ShortUrl findShortUrlById(@PathVariable int userId, @PathVariable int urlId) {
        return shortUrlService.findShortUrlByIdAndUserId(urlId, userId);
    }

    @DeleteMapping("user/{userId}/url/detail/{urlId}")
    @ApiOperation(value = "")
    public int deleteShortUrlById(@PathVariable int userId, @PathVariable int urlId) {
        return shortUrlService.deleteShortUrlByIdAndUserId(urlId, userId);
    }

    @GetMapping("/s/{shortUrl}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    @ApiOperation(value = "")
    public ModelAndView redirectToOriginalUrl(@PathVariable String shortUrl) {
        return shortUrlService.redirectToOriginalUrl(shortUrl);
    }
}
