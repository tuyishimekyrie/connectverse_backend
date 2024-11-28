package com.connectverse.connectverse.controller;

import com.connectverse.connectverse.Dto.ProfileDto;
import com.connectverse.connectverse.Exception.ProfileNotFoundException;
import com.connectverse.connectverse.config.JwtService;
import com.connectverse.connectverse.model.Profile;
import com.connectverse.connectverse.model.User;
import com.connectverse.connectverse.service.ProfileService;
import com.connectverse.connectverse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;
    @Autowired

    private final JwtService jwtService;

    @PostMapping("/add")
    public ResponseEntity<String> addProfile(@RequestHeader("Authorization") String token, @RequestBody ProfileDto profileDto) {
        // Get the logged-in user's username from the token
        System.out.println("Token Initialized" + token);
        String username = getUsernameFromToken(token);
        System.out.println("Token Retrived");
        // Convert ProfileDto to Profile entity
        Profile profile = convertToProfileEntity(profileDto);

        // Set the username for the profile
        profile.setUsername(username);

// Retrieve the user from the database using the username
        Optional<User> userOptional = userService.getUserByEmail(username);

// Check if the user exists
        if (userOptional.isPresent()) {
            // If the user exists, set it for the profile
            User user = userOptional.get();
            profile.setUser(user);
        } else {
            // Handle the case where the user does not exist
            // You can throw an exception, return an error response, or handle it according to your application's logic
            return ResponseEntity.ok("User Not Found");
        }

        // Save the profile entity to the database (Assuming you have a ProfileRepository)
        profileService.addProfile(profile);

        // Return a success response
        return ResponseEntity.ok("Profile added successfully");
    }

    // Method to convert ProfileDto to Profile entity
    private Profile convertToProfileEntity(ProfileDto profileDto) {
        return Profile.builder()
                .fullName(profileDto.getFullName())
                .username(profileDto.getUsername())
                .bio(profileDto.getBio())
                .location(profileDto.getLocation())
                .website(profileDto.getWebsite())
                .profileImageUrl(profileDto.getProfileImageUrl())
                .gender(profileDto.getGender())
                .joinDate(profileDto.getJoinDate())
                .build();
    }

    private String getUsernameFromToken(String token) {
        // Logic to extract username from JWT token
        String jwt = token.substring(7);
        System.out.println("Extracted  JWT: " + jwt);
        return jwtService.extractUsername(jwt);
    }


    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Integer profileId) {
        ProfileDto profile = profileService.getProfileById(profileId);
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody ProfileDto profileDTO) {
        try {
            Profile updatedProfile = profileService.updateProfile(id, profileDTO);
            return ResponseEntity.ok(updatedProfile);
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
