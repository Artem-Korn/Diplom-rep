package com.example.trainbrain.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TASK_TABLE")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String game_name;
    private String options;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User teacher;

    @ManyToMany
    @JoinTable(
            name = "USER_TASK_TABLE",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> students = new HashSet<>();

    public Task() {}

    public Task(String gameName, String options, User teacher) {
        this.game_name = gameName;
        this.options = options;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getTeacherName() {
        return teacher.getUsername();
    }
}
