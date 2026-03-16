package com.todo.todowebapp.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.todo.todowebapp.ErrorHandler.GlobalExceptionHandler;
import com.todo.todowebapp.ErrorHandler.ResourceNotFoundException;
import com.todo.todowebapp.model.Task;
import com.todo.todowebapp.service.TaskService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest {


    private TaskService taskService = Mockito.mock(TaskService.class);

    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new TaskController(taskService))
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();

    @Test
    void shouldGetAllTasks() throws Exception {

        Task task1 = new Task();
        task1.setTitle("Test Task");
        task1.setDescription("Testing");
        task1.setCompleted(false);

        Mockito.when(taskService.getAllTasks()).thenReturn(List.of(task1));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].description").value("Testing"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    void shouldGetUncompletedTasks() throws Exception {

        Task task1 = new Task();
        task1.setTitle("Test Task");
        task1.setDescription("Testing");
        task1.setCompleted(false);

        Mockito.when(taskService.getUncompletedTasks()).thenReturn(List.of(task1));

        mockMvc.perform(get("/api/tasks/uncompleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].description").value("Testing"))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    void shouldReturnEmptyListWhenNoTasks() throws Exception {
        Mockito.when(taskService.getAllTasks()).thenReturn(List.of());

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenNoUncompletedTasks() throws Exception {
        Mockito.when(taskService.getUncompletedTasks()).thenReturn(List.of());

        mockMvc.perform(get("/api/tasks/uncompleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shoudlReturnBadRequestWhenAddingInvalidTask() throws Exception {
        String invalidTaskJson = "{\"title\": \"\", \"description\": \"\"}";

        mockMvc.perform(post("/api/tasks")
                .contentType("application/json")
                .content(invalidTaskJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnOkWhenAddingValidTask() throws Exception {
        String validTaskJson = "{\"title\": \"Test Task\", \"description\": \"Testing\"}";

        Mockito.when(taskService.saveTask(any())).thenReturn(new Task());

        mockMvc.perform(post("/api/tasks")
                .contentType("application/json")
                .content(validTaskJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenDeletingNonExistentTask() throws Exception {

        Mockito.doThrow(
            new ResourceNotFoundException("Task not found")
        ).when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOkWhenDeletingExistingTask() throws Exception {

        Mockito.doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenCompletingNonExistentTask() throws Exception {

        Mockito.doThrow(
            new ResourceNotFoundException("Task not found")
        ).when(taskService).completeTask(1L);

        mockMvc.perform(post("/api/tasks/1/complete"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnOkWhenCompletingExistingTask() throws Exception {

        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Testing");
        task.setCompleted(true);

        String validTaskJson = "{\"title\": \"Test Task\", \"description\": \"Testing\", \"completed\": \"true\"}";

        Mockito.when(taskService.completeTask(1L)).thenReturn(task);

        mockMvc.perform(post("/api/tasks/complete/1")
                .contentType("application/json")
                .content(validTaskJson))
                .andExpect(status().isOk());
    }
}
