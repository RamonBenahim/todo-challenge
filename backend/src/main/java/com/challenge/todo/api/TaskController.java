package com.challenge.todo.api;

import com.challenge.todo.api.dto.TaskRequest;
import com.challenge.todo.api.dto.TaskResponse;
import com.challenge.todo.application.TaskService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@Controller("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Get
    public HttpResponse<List<TaskResponse>> listTasks() {
        return HttpResponse.ok(taskService.listTasks());
    }

    @Post
    public HttpResponse<TaskResponse> createTask(@Body @Valid TaskRequest request) {
        return HttpResponse.created(taskService.createTask(request));
    }

    @Put("/{id}")
    public HttpResponse<TaskResponse> updateTask(@PathVariable UUID id, @Body @Valid TaskRequest request) {
        try {
            return HttpResponse.ok(taskService.updateTask(id, request));
        } catch (IllegalArgumentException e) {
            return HttpResponse.notFound();
        }
    }

    @Delete("/{id}")
    public HttpResponse<Void> deleteTask(@PathVariable UUID id) {
        try {
            taskService.deleteTask(id);
            return HttpResponse.noContent();
        } catch (IllegalArgumentException e) {
            return HttpResponse.notFound();
        }
    }
}
