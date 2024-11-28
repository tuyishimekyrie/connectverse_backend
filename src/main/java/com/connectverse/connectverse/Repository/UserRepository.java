package com.connectverse.connectverse.Repository;

import com.connectverse.connectverse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findByUsernameContainingIgnoreCase(String username);
}
