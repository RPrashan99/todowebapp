package com.todo.todowebapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.todowebapp.DTO.TaskRequest;
import com.todo.todowebapp.ErrorHandler.ResourceNotFoundException;
import com.todo.todowebapp.model.Task;
import com.todo.todowebapp.repository.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getUncompletedTasks() {
        return taskRepository.findByCompleted(false);
    }

    public Task saveTask(TaskRequest taskRequest) {

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id){

        taskRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Task not found")
        );

        taskRepository.deleteById(id);
    }

    public Task completeTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Task not found")
        );
        task.setCompleted(true);
        taskRepository.save(task);

        return task;
    }

}
