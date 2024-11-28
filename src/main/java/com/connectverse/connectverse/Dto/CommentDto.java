package com.connectverse.connectverse.Dto;


import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer id;
    private String content;


}
