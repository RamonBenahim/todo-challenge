package com.challenge.todo.application;

import com.challenge.todo.api.dto.TaskRequest;
import com.challenge.todo.api.dto.TaskResponse;
import com.challenge.todo.domain.Task;
import com.challenge.todo.domain.TaskStatus;
import com.challenge.todo.infrastructure.TaskRepository;
import com.challenge.todo.infrastructure.messaging.TaskEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskEventProducer eventProducer;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        eventProducer = Mockito.mock(TaskEventProducer.class);
        taskService = new TaskService(taskRepository, eventProducer);
    }

    @Test
    void testCreateTask() {
        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");
        request.setDescription("Description");

        Task savedTask = new Task();
        savedTask.setId(UUID.randomUUID());
        savedTask.setTitle(request.getTitle());
        savedTask.setDescription(request.getDescription());
        savedTask.setStatus(TaskStatus.PENDING);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse response = taskService.createTask(request);

        assertNotNull(response);
        assertEquals("New Task", response.getTitle());
        assertEquals(TaskStatus.PENDING, response.getStatus());

        verify(taskRepository, times(1)).save(any(Task.class));
        verify(eventProducer, times(1)).sendTaskEvent(eq(savedTask.getId()), any());
    }
}
