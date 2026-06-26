package com.challenge.todo.domain;

import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

@Serdeable
public class TaskEvent {
    private UUID taskId;
    private String action; // e.g., "CREATED", "UPDATED"

    public TaskEvent() {}

    public TaskEvent(UUID taskId, String action) {
        this.taskId = taskId;
        this.action = action;
    }

    public UUID getTaskId() { return taskId; }
    public void setTaskId(UUID taskId) { this.taskId = taskId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
