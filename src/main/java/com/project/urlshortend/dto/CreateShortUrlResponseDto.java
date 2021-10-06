package com.project.urlshortend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateShortUrlResponseDto {

    private int id;
    private String shortend;
}
