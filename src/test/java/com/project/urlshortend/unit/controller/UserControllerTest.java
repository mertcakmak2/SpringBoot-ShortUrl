package com.project.urlshortend.unit.controller;

import com.project.urlshortend.controller.UserController;
import com.project.urlshortend.dto.RegisterRequestDto;
import com.project.urlshortend.dto.RegisterResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    UserController userController;

    @Test
    void should_return_new_user_controller(){

        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("mertcakmak").password("password").build();

        RegisterResponseDto registerResponseDto =  RegisterResponseDto.builder().userId(1).build();

        Mockito.when(userController.register(registerRequestDto)).thenReturn(registerResponseDto);

        RegisterResponseDto actualResponse = userController.register(registerRequestDto);

        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getUserId());

    }

}