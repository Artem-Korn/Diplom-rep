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

    private Integer difficulty;

    @OneToMany(mappedBy = "task")
    private Set<Mark> marks;

    @ManyToMany
    @JoinTable(
            name = "USER_TASK_TABLE",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> students = new HashSet<>();

    public Task() {}

    public Task(String game_name, String options, User teacher, Integer difficulty) {
        this.game_name = game_name;
        this.options = options;
        this.teacher = teacher;
        this.difficulty = difficulty;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameName() {
        return game_name;
    }

    public void setGameName(String game_name) {
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

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }
}
