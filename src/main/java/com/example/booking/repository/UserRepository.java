package com.example.booking.repository;

import com.example.booking.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {


    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail (String email);
}
