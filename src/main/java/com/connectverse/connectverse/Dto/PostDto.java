package com.connectverse.connectverse.Dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Integer id;
    private String content;
    private String category;
    private String headline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
