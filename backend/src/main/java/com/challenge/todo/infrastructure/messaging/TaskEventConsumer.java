package com.challenge.todo.infrastructure.messaging;

import com.challenge.todo.domain.TaskEvent;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class TaskEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(TaskEventConsumer.class);

    @Topic("task-events")
    public void receive(TaskEvent event) {
        LOG.info("Received Task Event - Task ID: {}, Action: {}", event.getTaskId(), event.getAction());

    }
}
