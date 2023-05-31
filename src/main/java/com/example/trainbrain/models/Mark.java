package com.example.trainbrain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MARK_TABLE")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String game_name;
    private Integer mark;
    private Integer difficulty;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Mark() {}

    public Mark(String game_name, Integer mark, Integer difficulty, LocalDateTime date, User user, Task task) {
        this.game_name = game_name;
        this.mark = mark;
        this.difficulty = difficulty;
        this.date = date;
        this.user = user;
        this.task = task;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getGameName() {
        return game_name;
    }

    public void setGameName(String game) {
        this.game_name = game;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }


}
