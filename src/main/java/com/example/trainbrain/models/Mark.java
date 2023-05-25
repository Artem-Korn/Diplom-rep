package com.example.trainbrain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "MARK_TABLE")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String game;
    private Integer mark;
    private Integer difficulty;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Mark() {}

    public Mark(String game, Integer mark, Integer difficulty, Date date, User user, Task task) {
        this.game = game;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
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
