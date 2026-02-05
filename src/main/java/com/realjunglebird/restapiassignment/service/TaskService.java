package com.realjunglebird.restapiassignment.service;

import com.realjunglebird.restapiassignment.dto.TaskRequest;
import com.realjunglebird.restapiassignment.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    // Получение списка задач по статусу (активные/завершенные)
    public List<Task> getTasksByStatus(boolean completed) {
        return tasks.values().stream()
                .filter(task -> task.isCompleted() == completed)
                .toList();
    }

    // Обновление задачи по идентификатору
    public Task updateTask(Long id, TaskRequest taskRequest) {
        Task task = tasks.get(id);
        if (task == null) {
            return null;
        }
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());
        return task;
    }

    // Частичное обновление задачи по идентификатору
    public Task partialUpdateTask(Long id, Map<String, Object> fields) {
        Task task = tasks.get(id);
        if (task == null) {
            return null;
        }
        fields.forEach((key, value) -> {
            switch (key) {
                case "title":
                    if (value != null) {
                        task.setTitle((String) value);
                    }
                    break;
                case "description":
                    task.setDescription(value == null ? null : (String) value);
                    break;
                case "completed":
                    if (value != null) {
                        task.setCompleted((Boolean) value);
                    }
                    break;
                default:
                    break;
            }
        });
        return task;
    }

    // Удаление задачи по идентификатору
    public boolean deleteTask(Long id) {
        return tasks.remove(id) != null;
    }
}
