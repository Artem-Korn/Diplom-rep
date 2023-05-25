package com.example.trainbrain.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDCLASS_TABLE")
public class StudClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User teacher;

    @ManyToMany
    @JoinTable(
            name = "USER_STUDCLASS_TABLE",
            joinColumns = {@JoinColumn(name = "studclass_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> students = new HashSet<>();

    public StudClass() {
    }

    public StudClass(String name, User teacher, Set<User> students) {
        this.name = name;
        this.teacher = teacher;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }
}
