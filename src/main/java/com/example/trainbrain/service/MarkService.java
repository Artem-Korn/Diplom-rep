package com.example.trainbrain.service;

import com.example.trainbrain.models.Mark;
import com.example.trainbrain.models.Task;
import com.example.trainbrain.models.User;
import com.example.trainbrain.repositories.MarkRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MarkService {
    private final MarkRepository markRepository;

    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public void createMark(Integer mark, String game, User user, Task task) {
        this.markRepository.save(new Mark(game, mark, task.getDifficulty(), new Date(), user, task));
    }
}
