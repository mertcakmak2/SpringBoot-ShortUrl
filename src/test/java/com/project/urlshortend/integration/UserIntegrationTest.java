package com.project.urlshortend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.urlshortend.controller.UserController;
import com.project.urlshortend.dto.RegisterRequestDto;
import com.project.urlshortend.dto.RegisterResponseDto;
import com.project.urlshortend.service.UserService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserController userController;

    @Test
    void should_return_new_user_integration() throws Exception {

        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("mertcakmak").password("password").build();

        RegisterResponseDto registerResponseDto = RegisterResponseDto.builder()
                .userId(1).build();

        Mockito.when(userController.register(registerRequestDto)).thenReturn(registerResponseDto);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerRequestDto)))
                .andReturn();

        RegisterResponseDto actualResponse =
                mapper.readValue(mvcResult.getResponse().getContentAsString(), RegisterResponseDto.class);

        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getUserId());
        assertEquals(1, actualResponse.getUserId());
        assertEquals(201, mvcResult.getResponse().getStatus());
    }
}
