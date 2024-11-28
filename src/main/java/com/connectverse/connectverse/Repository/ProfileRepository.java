package com.connectverse.connectverse.Repository;

import com.connectverse.connectverse.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
