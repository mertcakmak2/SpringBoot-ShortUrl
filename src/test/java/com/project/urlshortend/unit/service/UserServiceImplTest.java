package com.project.urlshortend.unit.service;

import com.project.urlshortend.dto.RegisterRequestDto;
import com.project.urlshortend.dto.RegisterResponseDto;
import com.project.urlshortend.model.User;
import com.project.urlshortend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {

    @MockBean
    UserService userService;

    @Test
    void should_return_saved_user_service(){

        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("mertcakmak")
                .password("password").build();

        RegisterResponseDto expectedResponse = RegisterResponseDto.builder()
                .userId(1).build();

        Mockito.when(userService.isExistUser(registerRequestDto.getUsername())).thenReturn(false);
        Mockito.when(userService.register(registerRequestDto)).thenReturn(expectedResponse);

        RegisterResponseDto actualResponse =  userService.register(registerRequestDto);

        assertNotNull(actualResponse.getUserId());

    }

    @Test
    void should_return_false_to_exist_user_by_username_service(){
        String username = "mertcakmak";

        Mockito.when(userService.isExistUser(username)).thenReturn(false);

        Boolean isExist = userService.isExistUser(username);

        assertFalse(isExist);
    }

    @Test
    void should_return_user_to_exist_user_by_id_service(){

        User user = User.builder().id(1).username("mertcakmak").password("password").build();
        int id = 1;

        Mockito.when(userService.findUserById(id)).thenReturn(user);

        User actualResponse = userService.findUserById(id);

        assertNotNull(actualResponse);
        assertEquals(id, user.getId());
    }

}