package com.example.trainbrain.repositories;

import com.example.trainbrain.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
