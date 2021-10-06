package com.project.urlshortend.unit.service;

import com.project.urlshortend.dto.CreateShortUrlRequestDto;
import com.project.urlshortend.dto.CreateShortUrlResponseDto;
import com.project.urlshortend.model.ShortUrl;
import com.project.urlshortend.model.User;
import com.project.urlshortend.service.ShortUrlService;
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
class ShortUrlServiceImplTest {

    @MockBean
    ShortUrlService shortUrlService;

    @Test
    void should_create_shortUrl_service(){

        int userId = 1;

        CreateShortUrlRequestDto createShortUrlRequestDto = CreateShortUrlRequestDto.builder()
                .url("https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular").build();

        CreateShortUrlResponseDto expectedResponse = CreateShortUrlResponseDto.builder()
                .id(1)
                .shortend("http://localhost:8080/s/shorturl").build();

        Mockito.when(shortUrlService.createShortUrl(createShortUrlRequestDto, userId)).thenReturn(expectedResponse);

        CreateShortUrlResponseDto actualResponse = shortUrlService.createShortUrl(createShortUrlRequestDto, userId);

        assertNotNull(actualResponse.getId());
        assertNotNull(actualResponse.getShortend());
    }

    @Test
    void should_return_false_to_exist_shorturl_service(){
        String shortUrl = "http://localhost:8080/s/shorturl";

        Mockito.when(shortUrlService.isExistShortUrl(shortUrl)).thenReturn(false);

        assertFalse(shortUrlService.isExistShortUrl(shortUrl));
    }

    @Test
    void should_return_shorturl_list_by_user_id_service(){

        int userId = 1;
        User user = User.builder().id(1).username("mertcakmak").password("password").build();

        List<ShortUrl> urls = Arrays.asList(
                new ShortUrl(
                        1,
                        user,
                        "https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular",
                        "http://localhost:8080/s/f097b97a-2610-11ec-b227-d307ad7af970",
                        "f097b97a-2610-11ec-b227-d307ad7af970"
                        )
        );
        Mockito.when(shortUrlService.findShortUrlsByUserId(userId)).thenReturn(urls);
        List<ShortUrl> actualResponse = shortUrlService.findShortUrlsByUserId(userId);

        assertNotNull(actualResponse);
        assertEquals(userId, actualResponse.get(0).getUser().getId());
    }

    @Test
    void should_return_shorturl_by_id_service(){

        int shortUrlId = 1;
        User user = User.builder().id(1).username("mertcakmak").password("password").build();

        ShortUrl shortUrl = new ShortUrl(
                1,
                user,
                "https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular",
                "http://localhost:8080/s/f097b97a-2610-11ec-b227-d307ad7af970",
                "f097b97a-2610-11ec-b227-d307ad7af970"
        );

        Mockito.when(shortUrlService.findShortUrlByIdAndUserId(shortUrlId, user.getId())).thenReturn(shortUrl);
        ShortUrl actualResponse = shortUrlService.findShortUrlByIdAndUserId(shortUrlId, user.getId());

        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getShortUrl());
        assertEquals(1, shortUrl.getId());
    }

    @Test
    void should_return_shorturl_id_to_delete_operation_service(){

        int shortUrlId = 1;
        int userId = 1;

        Mockito.when(shortUrlService.deleteShortUrlByIdAndUserId(shortUrlId, userId)).thenReturn(1);
        int actualResponse = shortUrlService.deleteShortUrlByIdAndUserId(shortUrlId, userId);

        assertNotNull(actualResponse);
        assertEquals(shortUrlId, actualResponse);
    }

    @Test
    void should_return_modelandview_service(){
        String shortUrlString = "f097b97a-2610-11ec-b227-d307ad7af970";
        String originalUrlString = "https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular";

        ModelAndView modelAndView = new ModelAndView("redirect:"+originalUrlString);
        Mockito.when(shortUrlService.redirectToOriginalUrl(shortUrlString)).thenReturn(modelAndView);

        ModelAndView actualResponse = shortUrlService.redirectToOriginalUrl(shortUrlString);

        assertNotNull(actualResponse);
    }

}