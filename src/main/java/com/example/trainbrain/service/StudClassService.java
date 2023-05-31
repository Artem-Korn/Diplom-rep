package com.example.trainbrain.service;

import com.example.trainbrain.models.StudClass;
import com.example.trainbrain.models.User;
import com.example.trainbrain.repositories.StudClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudClassService {

    private final StudClassRepository studClassRepository;

    public StudClassService(StudClassRepository studClassRepository) {
        this.studClassRepository = studClassRepository;
    }

    public void addClass(StudClass studclass, User user) {
        studclass.setTeacher(user);
        studClassRepository.save(studclass);
    }

    public void addUserToClass(StudClass studclass, User user) {
        studclass.getStudents().add(user);
        studClassRepository.save(studclass);
    }

    public void removeUserFromClass(StudClass studclass, User user) {
        studclass.getStudents().remove(user);
        studClassRepository.save(studclass);
    }

    public void removeClass(StudClass studclass) {
        studClassRepository.delete(studclass);
    }

    public List<StudClass> getStudClassesFromTeacher(User teacher) {
        return teacher.getMyStudclasses().stream()
                .sorted((s1, s2) -> s1.getName().compareTo(s2.getName()))
                .collect(Collectors.toList());
    }
}
