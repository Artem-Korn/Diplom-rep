package com.example.trainbrain.repositories;

import com.example.trainbrain.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    Page<User> findAllByUsernameContains(String username, Pageable pageable);
    Page<User> findAll(Pageable pageable);
}
