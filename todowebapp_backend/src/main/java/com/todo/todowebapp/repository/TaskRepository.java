package com.todo.todowebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.todowebapp.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
}
