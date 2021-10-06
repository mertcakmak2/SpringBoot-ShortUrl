package com.project.urlshortend;

import com.project.urlshortend.dto.RegisterRequestDto;
import com.project.urlshortend.dto.RegisterResponseDto;
import com.project.urlshortend.repository.UserRepository;
import com.project.urlshortend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class UrlshortendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlshortendApplication.class, args);
    }


}
