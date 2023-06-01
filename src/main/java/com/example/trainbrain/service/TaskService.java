package com.example.trainbrain.service;

import com.example.trainbrain.models.Task;
import com.example.trainbrain.models.User;
import com.example.trainbrain.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(String name, Integer difficulty, Map<String, String> form, User user) {
        String options_str = "";
        List<String> list = new ArrayList<>(form.values()).subList(3, form.size());
        for (String option : list) options_str += option + ';';
        options_str = options_str.substring(0, options_str.length() - 1);

        Task newTask = new Task(name, options_str, user, difficulty);
        user.getMyTasks().add(newTask);

        taskRepository.save(newTask);
        return newTask;
    }

    public void sendTask(Set<User> users, Task task) {
        task.getStudents().clear();
        if(users != null) task.setStudents(users);
        taskRepository.save(task);
    }

    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    public String convertGameNameToTemplate(String gameName) {
        return switch (gameName) {
            case "Таблиця Шульте" -> "shulteTable";
            case "Знайди крота" -> "moleGame";
            case "Де більше?" -> "compareGame";
            default -> throw new Error("Помилка при пошуку шаблона за назвою гри");
        };
    }

    public Iterable<String> getOptions(Task task) {
        return List.of(task.getOptions().split(";"));
    }

    public void removeTask(Task task) {
        task.getMarks().forEach(mark -> mark.setTask(null));
        taskRepository.delete(task);
    }

    public void removeUserTask(Task task, User user) {
        task.getStudents().removeIf(u -> (Objects.equals(u.getId(), user.getId())));
        taskRepository.save(task);
    }

    public List<Task> getTasksFromStudent(User student) {
        return student.getTasks().stream()
                .sorted((t1, t2) -> t1.getId().compareTo(t2.getId()))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksFromTeacher(User teacher) {
        return teacher.getMyTasks().stream()
                .sorted((t1, t2) -> t1.getId().compareTo(t2.getId()))
                .collect(Collectors.toList());
    }
}
