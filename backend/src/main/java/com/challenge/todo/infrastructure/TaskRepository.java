package com.challenge.todo.infrastructure;

import com.challenge.todo.domain.Task;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {
}
