package com.connectverse.connectverse.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetDto {
    private Integer id;
    private String message;
    private int likesCount;
    private String imageUrl;
    private String videoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
