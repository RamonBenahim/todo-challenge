package com.challenge.todo.infrastructure.messaging;

import com.challenge.todo.domain.TaskEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

import java.util.UUID;

@KafkaClient
public interface TaskEventProducer {

    @Topic("task-events")
    void sendTaskEvent(@KafkaKey UUID taskId, TaskEvent event);
}
