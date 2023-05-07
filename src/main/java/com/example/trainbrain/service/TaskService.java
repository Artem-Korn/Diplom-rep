package com.example.trainbrain.service;

import com.example.trainbrain.models.Task;
import com.example.trainbrain.models.User;
import com.example.trainbrain.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(String name, Map<String, String> form, User user) {
        String options_str = "";
        List<String> list = new ArrayList<>(form.values()).subList(2, form.size());
        for (String option : list) options_str += option + ';';
        options_str = options_str.substring(0, options_str.length() - 1);
        Task newTask = new Task(name, options_str, user);
        user.getMyTasks().add(newTask);

        taskRepository.save(newTask);
        return newTask;
    }

    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }
}
