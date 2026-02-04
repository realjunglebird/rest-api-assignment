package com.realjunglebird.restapiassignment.service;

import com.realjunglebird.restapiassignment.dto.TaskRequest;
import com.realjunglebird.restapiassignment.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final ConcurrentHashMap<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // Добавление новой задачи
    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task(
                idCounter.getAndIncrement(),
                taskRequest.getTitle(),
                taskRequest.getDescription(),
                taskRequest.isCompleted()
        );
        tasks.put(task.getId(), task);
        return task;
    }

    // Получение всех задач
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Получение задачи по идентификатору
    public Task getTaskById(Long id) {
        return tasks.get(id);
    }

    // Обновление задачи по идентификатору
    public Task updateTask(Long id, TaskRequest taskRequest) {
        Task existingTask = tasks.get(id);
        if (existingTask == null) {
            return null;
        }
        existingTask.setTitle(taskRequest.getTitle());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setCompleted(taskRequest.isCompleted());
        return existingTask;
    }

    // Удаление задачи по идентификатору
    public boolean deleteTask(Long id) {
        return tasks.remove(id) != null;
    }
}
