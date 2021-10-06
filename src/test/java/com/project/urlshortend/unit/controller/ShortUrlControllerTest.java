package com.project.urlshortend.unit.controller;

import com.project.urlshortend.controller.ShortUrlController;
import com.project.urlshortend.dto.CreateShortUrlRequestDto;
import com.project.urlshortend.dto.CreateShortUrlResponseDto;
import com.project.urlshortend.model.ShortUrl;
import com.project.urlshortend.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShortUrlControllerTest {

    @MockBean
    ShortUrlController shortUrlController;

    @Test
    void should_create_new_shorturl_controller(){

        CreateShortUrlRequestDto createShortUrlRequestDto = CreateShortUrlRequestDto.builder()
                .url("https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular").build();

        int userId = 1;

        CreateShortUrlResponseDto expectedResponse = CreateShortUrlResponseDto.builder()
                .id(userId)
                .shortend("http://localhost:8080/s/shorturl").build();

        Mockito.when(shortUrlController.createShortUrl(createShortUrlRequestDto, userId)).thenReturn(expectedResponse);

        CreateShortUrlResponseDto actualResponse = shortUrlController.createShortUrl(createShortUrlRequestDto, userId);

        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getShortend());
    }

    @Test
    void should_return_shorturl_list_by_user_id_controller(){

        int userId = 1;

        List<ShortUrl> urls = Arrays.asList(
                getShortUrl()
        );
        Mockito.when(shortUrlController.findShortUrlsByUserId(userId)).thenReturn(urls);
        List<ShortUrl> actualResponse = shortUrlController.findShortUrlsByUserId(userId);

        assertNotNull(actualResponse);
        assertEquals(userId, actualResponse.get(0).getUser().getId());
    }

    @Test
    void should_return_shorturl_by_id_controller(){

        int shortUrlId = 1;
        User user = getUser();

        ShortUrl shortUrl = getShortUrl();

        Mockito.when(shortUrlController.findShortUrlById(user.getId(), shortUrlId)).thenReturn(shortUrl);
        ShortUrl actualResponse = shortUrlController.findShortUrlById(user.getId(), shortUrlId);

        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getShortUrl());
        assertEquals(1, shortUrl.getId());
    }

    @Test
    void should_return_shorturl_id_to_delete_operation_controller(){
        int userId = 1;
        int shortUrlId = 1;

        Mockito.when(shortUrlController.deleteShortUrlById(shortUrlId, userId)).thenReturn(1);
        int actualResponse = shortUrlController.deleteShortUrlById(shortUrlId, userId);
        assertNotNull(actualResponse);
        assertEquals(shortUrlId, actualResponse);
    }

    @Test
    void should_return_modelandview_controller(){

        String shortUrlString = "f097b97a-2610-11ec-b227-d307ad7af970";
        String originalUrlString = "https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular";

        ModelAndView modelAndView = new ModelAndView("redirect:"+originalUrlString);
        Mockito.when(shortUrlController.redirectToOriginalUrl(shortUrlString)).thenReturn(modelAndView);

        ModelAndView actualResponse = shortUrlController.redirectToOriginalUrl(shortUrlString);
        assertNotNull(actualResponse);
    }


    ShortUrl getShortUrl(){
        return new ShortUrl(
                1,
                getUser(),
                "https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular",
                "http://localhost:8080/s/f097b97a-2610-11ec-b227-d307ad7af970",
                "f097b97a-2610-11ec-b227-d307ad7af970"
        );
    }

    User getUser(){
        return User.builder().id(1).username("mertcakmak").password("password").build();
    }

}