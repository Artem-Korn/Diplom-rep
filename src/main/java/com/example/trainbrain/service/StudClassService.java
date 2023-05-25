package com.example.trainbrain.service;

import com.example.trainbrain.models.StudClass;
import com.example.trainbrain.models.User;
import com.example.trainbrain.repositories.StudClassRepository;
import org.springframework.stereotype.Service;

@Service
public class StudClassService {

    private final StudClassRepository studClassRepository;
    private final TaskService taskService;

    public StudClassService(StudClassRepository studClassRepository, TaskService taskService) {
        this.studClassRepository = studClassRepository;
        this.taskService = taskService;
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
}
