package com.todo.todowebapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.todo.todowebapp.DTO.TaskRequest;
import com.todo.todowebapp.model.Task;
import com.todo.todowebapp.repository.TaskRepository;

public class TaskServiceTest {

    private TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private TaskService taskService = new TaskService(taskRepository);

    @Test
    void testGetAllTasks() {

        Task task1 = new Task();
        task1.setTitle("Test Task 1");
        task1.setDescription("Description for Test Task 1");
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setTitle("Test Task 2");
        task2.setDescription("Description for Test Task 2");
        task2.setCompleted(true);

        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> result = taskService.getAllTasks();
        assertEquals(2, result.size());
        assertEquals(task2, result.get(1));
        assertEquals("Test Task 1", result.get(0).getTitle());
        assertEquals("Test Task 2", result.get(1).getTitle());
    }

    @Test
    void testGetUncompletedTasks() {

        Task task1 = new Task();
        task1.setTitle("Test Task 1");
        task1.setDescription("Description for Test Task 1");
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setTitle("Test Task 2");
        task2.setDescription("Description for Test Task 2");
        task2.setCompleted(true);

        Mockito.when(taskRepository.findByCompleted(false)).thenReturn(Arrays.asList(task1));

        List<Task> result = taskService.getUncompletedTasks();
        assertEquals(1, result.size());
        assertEquals(task1, result.get(0));
    }

    @Test
    void testSaveTask() {

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Description for Test Task");
        task.setCompleted(false);

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Description for Test Task");

        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.saveTask(taskRequest);
        assertEquals("Test Task", result.getTitle());
        assertEquals("Description for Test Task", result.getDescription());
        assertEquals(false, result.getCompleted());
    }

    @Test
    void testDeleteTask() {

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));
        Mockito.doNothing().when(taskRepository).deleteById(1L);
        taskService.deleteTask(1L);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);

    }

    @Test
    void testCompleteTask() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Description for Test Task");
        task.setCompleted(false);

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.completeTask(1L);
        assertEquals(true, result.getCompleted());
    }
    
}
