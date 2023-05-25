package com.example.trainbrain.service;

import com.example.trainbrain.models.Role;
import com.example.trainbrain.models.StudClass;
import com.example.trainbrain.models.User;
import com.example.trainbrain.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.lang.module.FindException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Логін користувача не знайдено: " + username
                ));
    }

    public User getUserById(Long Id) {
        return userRepository
                .findById(Id)
                .orElseThrow(()->new FindException(
                        "Користувача з таким Id не знайдено: " + Id
                ));
    }

    public boolean addUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())) return false;

        user.setRoles(Collections.singleton(Role.ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return true;
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if(roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
    }

    public void updateProfile(User user, String password) {
        if(!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
    }

    public Iterable<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> findAllStudents() {
        Iterable<User> all = userRepository.findAll();
        List<User> students = new ArrayList<>();
        for (User user : all) {
            if(user.getRoles().contains(Role.STUDENT))
                students.add(user);
        }
        return students;
    }

    public void removeUser(User user) {
        userRepository.delete(user);
    }

    public List<User> findAllStudentsFromClasses(User teacher) {
        Iterable<StudClass> studclasses = teacher.getMyStudclasses();
        List<User> students = new ArrayList<>();

        for (StudClass studclass : studclasses) {
            for (User user : studclass.getStudents()) {
                if (!students.contains(user))
                    students.add(user);
            }
        }
        return students;
    }
}
