package com.challenge.todo.application;

import com.challenge.todo.api.dto.TaskRequest;
import com.challenge.todo.api.dto.TaskResponse;
import com.challenge.todo.domain.Task;
import com.challenge.todo.domain.TaskEvent;
import com.challenge.todo.domain.TaskStatus;
import com.challenge.todo.infrastructure.TaskRepository;
import com.challenge.todo.infrastructure.messaging.TaskEventProducer;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskEventProducer eventProducer;

    public TaskService(TaskRepository taskRepository, TaskEventProducer eventProducer) {
        this.taskRepository = taskRepository;
        this.eventProducer = eventProducer;
    }

    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING);
        
        Task savedTask = taskRepository.save(task);
        eventProducer.sendTaskEvent(savedTask.getId(), new TaskEvent(savedTask.getId(), "CREATED"));
        
        return TaskResponse.fromEntity(savedTask);
    }

    public List<TaskResponse> listTasks() {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTask(UUID id, TaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        
        Task updatedTask = taskRepository.update(task);
        eventProducer.sendTaskEvent(updatedTask.getId(), new TaskEvent(updatedTask.getId(), "UPDATED"));
        
        return TaskResponse.fromEntity(updatedTask);
    }

    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        taskRepository.delete(task);
        eventProducer.sendTaskEvent(id, new TaskEvent(id, "DELETED"));
    }
}
