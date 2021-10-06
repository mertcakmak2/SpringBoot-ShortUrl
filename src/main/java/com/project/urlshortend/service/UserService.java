package com.project.urlshortend.service;

import com.project.urlshortend.dto.RegisterRequestDto;
import com.project.urlshortend.dto.RegisterResponseDto;
import com.project.urlshortend.model.User;

public interface UserService {

    RegisterResponseDto register(RegisterRequestDto registerRequestDto);
    Boolean isExistUser(String username);
    User findUserById(int id);

}
