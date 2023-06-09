package com.example.trainbrain.service;

import com.example.trainbrain.models.Mark;
import com.example.trainbrain.models.Task;
import com.example.trainbrain.models.User;
import com.example.trainbrain.repositories.MarkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class MarkService {
    private final MarkRepository markRepository;

    public MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public void createMark(Integer mark, String game_name, User user, Task task) {
        this.markRepository.save(new Mark(game_name, mark, task.getDifficulty(), LocalDateTime.now(ZoneId.of("Europe/Simferopol")), user, task));
    }

    public Map<String, List<Object>> getChartData(User user) {
        Map<String, List<Object>> chartData = new HashMap<>();

        Map<String, List<Mark>> groupedMark = user.getMarks().stream()
                .collect(groupingBy(Mark::getGameName));

        for (String key : groupedMark.keySet()) {
            chartData.put(key, groupedMark.get(key).stream()
                    .filter(mark -> mark.getMark() > -1)
                    .sorted((m1, m2) -> m1.getDate().compareTo(m2.getDate()))
                    .map(mark -> Arrays.asList(mark.getDate(), mark.getMark(), mark.getDifficulty()))
                    .collect(Collectors.toList()));
        }

        return chartData;
    }

    public Page<Mark> findByUser(User user, Pageable pageable) {
        return markRepository.findByUser(user, pageable);
    }
}
