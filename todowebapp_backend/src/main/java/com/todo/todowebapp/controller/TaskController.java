package com.todo.todowebapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.todowebapp.DTO.TaskRequest;
import com.todo.todowebapp.model.Task;
import com.todo.todowebapp.service.TaskService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/uncompleted")
    public List<Task> getUncompletedTasks() {
        return taskService.getUncompletedTasks();
    }

    @PostMapping
    public ResponseEntity<?> addTask(@Valid  @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.saveTask(taskRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.completeTask(id));
    }
    

}
