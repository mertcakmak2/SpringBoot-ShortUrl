package com.project.urlshortend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDto {

    private String username;
    private String password;

}
