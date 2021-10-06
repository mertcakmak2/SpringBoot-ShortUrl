package com.project.urlshortend.service;

import com.project.urlshortend.dto.RegisterRequestDto;
import com.project.urlshortend.dto.RegisterResponseDto;
import com.project.urlshortend.model.User;
import com.project.urlshortend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {

        if (isExistUser(registerRequestDto.getUsername())) throw new IllegalStateException("User is already exist.");

        User user = User.builder()
                .username(registerRequestDto.getUsername())
                .password(registerRequestDto.getPassword()).build();

        User savedUser = userRepository.save(user);

        return RegisterResponseDto.builder().userId(savedUser.getId()).build();
    }

    @Override
    public Boolean isExistUser(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
}
