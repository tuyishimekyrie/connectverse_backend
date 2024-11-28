package com.connectverse.connectverse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId;

    @Column(nullable = true,length = 280)
    private String fullName;
    @Column(nullable = true,length = 280)
    private String username;
    @Column(nullable = true,length = 280)
    private String bio;
    @Column(nullable = true)
    private String location;
    @Column(nullable = true)
    private String website;
    @Column(nullable = true)
    private String profileImageUrl;
    @Column(nullable = true)
    private String gender;
    private String joinDate;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int hashCode() {
        return Objects.hash(profileId, fullName);
    }
    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", website='" + website + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", joinDate='" + joinDate + '\'' +
                '}';
    }

}
