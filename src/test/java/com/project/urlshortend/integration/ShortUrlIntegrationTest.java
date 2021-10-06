package com.project.urlshortend.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.urlshortend.controller.ShortUrlController;
import com.project.urlshortend.dto.CreateShortUrlRequestDto;
import com.project.urlshortend.dto.CreateShortUrlResponseDto;
import com.project.urlshortend.model.ShortUrl;
import com.project.urlshortend.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShortUrlIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ShortUrlController shortUrlController;

    @Test
    void should_create_shorturl_integration() throws Exception {
        // "user/{userId}/url/create"

        int userId = 1;
        CreateShortUrlRequestDto createShortUrlRequestDto = CreateShortUrlRequestDto.builder()
                .url("https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular").build();

        CreateShortUrlResponseDto expectedResponse = CreateShortUrlResponseDto.builder()
                .id(1)
                .shortend("http://localhost:8080/s/shorturl").build();

        Mockito.when(shortUrlController.createShortUrl(createShortUrlRequestDto, userId)).thenReturn(expectedResponse);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user/"+userId+"/url/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createShortUrlRequestDto)))
                .andReturn();

        CreateShortUrlResponseDto actualResponse =
                mapper.readValue(mvcResult.getResponse().getContentAsString(), CreateShortUrlResponseDto.class);

        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getShortend());
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    void should_return_shorturl_list_by_user_id_integration() throws Exception {
        // "user/{userId}/url/list"
        int userId = 1;

        List<ShortUrl> urls = Arrays.asList(getShortUrl());

        Mockito.when(shortUrlController.findShortUrlsByUserId(userId)).thenReturn(urls);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/user/"+userId+"/url/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ArrayList actualResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), ArrayList.class);
        LinkedHashMap<Object, Object> item = (LinkedHashMap<Object, Object>) actualResponse.get(0);
        ShortUrl url = mapper.convertValue(item, ShortUrl.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(1, url.getId());
    }

    @Test
    void should_return_shorturl_by_id_integration() throws Exception {
        // "user/{userId}/url/detail/{urlId}"

        User user = getUser();
        ShortUrl shortUrl = getShortUrl();

        Mockito.when(shortUrlController.findShortUrlById(user.getId(),shortUrl.getId())).thenReturn(shortUrl);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/user/"+user.getId()+"/url/detail/"+shortUrl.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ShortUrl actualResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), ShortUrl.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(shortUrl.getId(), actualResponse.getId());
    }

    @Test
    void should_return_shorturl_id_to_delete_operation_integration() throws Exception {
        // DELETE "/user/{userId}/url/detail/{urlId}"

        User user = getUser();
        ShortUrl shortUrl = getShortUrl();

        Mockito.when(shortUrlController
                .deleteShortUrlById(user.getId(),shortUrl.getId())).thenReturn(shortUrl.getId());

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/"+user.getId()+"/url/detail/"+shortUrl.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(1, Integer.valueOf(mvcResult.getResponse().getContentAsString()));

    }

    @Test
    void should_return_modelandview_integration() throws Exception {

        // "/s/{shortUrl}"

        String shortUrlString = "f097b97a-2610-11ec-b227-d307ad7af970";
        String originalUrlString = "https://www.tapu.com/l/uygulamaya-ozel-kampanyali-tapular";

        ModelAndView modelAndView = new ModelAndView("redirect:"+originalUrlString);
        Mockito.when(shortUrlController.redirectToOriginalUrl(shortUrlString)).thenReturn(modelAndView);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/s/"+shortUrlString)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        assertEquals(301, mvcResult.getResponse().getStatus());
        assertNotNull(mvcResult.getResponse());
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
