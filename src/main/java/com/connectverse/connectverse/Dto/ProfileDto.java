package com.connectverse.connectverse.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Integer profileId;
    private String fullName;
    private String username;
    private String bio;
    private String location;
    private String website;
    private String profileImageUrl;
    private String gender;
    private String joinDate;
}
