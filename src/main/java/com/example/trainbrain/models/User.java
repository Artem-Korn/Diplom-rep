package com.example.trainbrain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_TABLE")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Логін не може бути порожнім")
    private String username;

    @NotBlank(message = "Ім'я не може бути порожнім")
    private String first_name;

    @NotBlank(message = "Прізвище не може бути порожнім")
    private String last_name;

    @NotBlank(message = "По батькові не може бути порожнім")
    private String patronymic;

    @NotBlank(message = "Пароль не може бути порожнім")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_ROLE_TABLE", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Task> my_tasks;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<StudClass> my_studclasses;

    @ManyToMany
    @JoinTable(
            name = "USER_TASK_TABLE",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id")}
    )
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "USER_STUDCLASS_TABLE",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "studclass_id")}
    )
    private Set<StudClass> studclasses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Mark> marks;

    public User() {}

    public User(String username, String first_name, String last_name, String patronymic, String password, Set<Role> roles) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.patronymic = patronymic;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Task> getMyTasks() {
        return my_tasks;
    }

    public void setMyTasks(Set<Task> my_tasks) {
        this.my_tasks = my_tasks;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<StudClass> getMyStudclasses() {
        return my_studclasses;
    }

    public void setMyStudclasses(Set<StudClass> my_studclasses) {
        this.my_studclasses = my_studclasses;
    }

    public Set<StudClass> getStudclasses() {
        return studclasses;
    }

    public void setStudclasses(Set<StudClass> studclasses) {
        this.studclasses = studclasses;
    }

    public Set<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
