package com.connectverse.connectverse.service;

import com.connectverse.connectverse.Dto.ProfileDto;
import com.connectverse.connectverse.Exception.ProfileNotFoundException;
import com.connectverse.connectverse.Repository.ProfileRepository;
import com.connectverse.connectverse.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public String addProfile(Profile profile) {
        try {
            profileRepository.save(profile);
            return "Profile saved successfully";
        } catch (Exception e) {
            return "Failed to save profile: " + e.getMessage();
        }
    }

    public ProfileDto getProfileById(Integer profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Convert Profile to ProfileDto
        ProfileDto profileDto = convertToDto(profile);
        return profileDto;
    }

    private ProfileDto convertToDto(Profile profile) {
        return ProfileDto.builder()
                .profileId(profile.getProfileId())
                .fullName(profile.getFullName())
                .username(profile.getUsername())
                .bio(profile.getBio())
                .location(profile.getLocation())
                .website(profile.getWebsite())
                .profileImageUrl(profile.getProfileImageUrl())
                .gender(profile.getGender())
                .joinDate(profile.getJoinDate().toString()) // Assuming joinDate is a Date type
                .build();
    }

    public Profile updateProfile(Integer id, ProfileDto profileDTO) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            if (profileDTO.getFullName() != null) {
                profile.setFullName(profileDTO.getFullName());
            }
            if (profileDTO.getBio() != null) {
                profile.setBio(profileDTO.getBio());
            }
            if (profileDTO.getLocation() != null) {
                profile.setLocation(profileDTO.getLocation());
            }
            if (profileDTO.getGender() != null) {
                profile.setGender(profileDTO.getGender());
            }
            if (profileDTO.getJoinDate() != null) {
                profile.setJoinDate(profileDTO.getJoinDate());
            }
            if (profileDTO.getUsername() != null) {
                profile.setUsername(profileDTO.getUsername());
            }
            if (profileDTO.getWebsite() != null) {
                profile.setWebsite(profileDTO.getWebsite());
            }
            if (profileDTO.getProfileImageUrl() != null) {
                profile.setProfileImageUrl(profileDTO.getProfileImageUrl());
            }
            return profileRepository.save(profile);
        } else {
            throw new ProfileNotFoundException("Profile not found");
        }
    }
}
