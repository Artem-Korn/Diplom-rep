package com.example.trainbrain.repositories;

import com.example.trainbrain.models.Mark;
import com.example.trainbrain.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MarkRepository extends CrudRepository<Mark, Long> {
    Page<Mark> findByUser(User user, Pageable pageable);
}
