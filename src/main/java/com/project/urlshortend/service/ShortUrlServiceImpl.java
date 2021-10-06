package com.project.urlshortend.service;

import com.fasterxml.uuid.Generators;
import com.project.urlshortend.dto.CreateShortUrlRequestDto;
import com.project.urlshortend.dto.CreateShortUrlResponseDto;
import com.project.urlshortend.model.ShortUrl;
import com.project.urlshortend.model.User;
import com.project.urlshortend.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortUrlServiceImpl implements ShortUrlService{

    private final ShortUrlRepository shortUrlRepository;
    private final UserService userService;

    @Override
    public CreateShortUrlResponseDto createShortUrl(CreateShortUrlRequestDto createShortUrlRequestDto, int userId) {

        User user = userService.findUserById(userId);
        if(user == null) throw new IllegalStateException("User not found");

        String generatedString = Generators.timeBasedGenerator().generate().toString();
        String shortUrlString = "http://localhost:8080/s/"+generatedString;

        ShortUrl shortUrl = ShortUrl.builder()
                .shortUrl(shortUrlString)
                .originalUrl(createShortUrlRequestDto.getUrl())
                .generatedString(generatedString)
                .user(user).build();

        ShortUrl savedUrl = shortUrlRepository.save(shortUrl);

        return CreateShortUrlResponseDto.builder().id(savedUrl.getId()).shortend(savedUrl.getShortUrl()).build();
    }

    @Override
    public Boolean isExistShortUrl(String shortUrl) {
        return shortUrlRepository.existsByShortUrl(shortUrl);
    }

    @Override
    public List<ShortUrl> findShortUrlsByUserId(int userId) {

        User user = userService.findUserById(userId);
        if(user == null) throw new IllegalStateException("User not found");

        return shortUrlRepository.findByUserId(userId);
    }

    @Override
    public ShortUrl findShortUrlByIdAndUserId(int shortUrlId, int userId) {
        return shortUrlRepository.findByIdAndUser_Id(shortUrlId, userId);
    }

    @Override
    public int deleteShortUrlByIdAndUserId(int shortUrlId, int userId) {
        ShortUrl shortUrl = shortUrlRepository.findByIdAndUser_Id(shortUrlId, userId);
        shortUrlRepository.deleteById(shortUrl.getId());
        return shortUrlId;
    }

    @Override
    public ModelAndView redirectToOriginalUrl(String shortUrl) {
        ShortUrl existShortUrl = shortUrlRepository.findByGeneratedString(shortUrl);

        return new ModelAndView("redirect:"+existShortUrl.getOriginalUrl());
    }
}
