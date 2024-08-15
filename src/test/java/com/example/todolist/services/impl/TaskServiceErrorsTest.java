package com.example.todolist.services.impl;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.enums.State;
import com.example.todolist.entity.exceptions.NotFoundException;
import com.example.todolist.entity.exceptions.TaskException;
import com.example.todolist.repository.ITaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplErrorsTest {

    @InjectMocks
    private TaskServiceImpl service;

    @Mock
    private ITaskRepository repository;

    @Test
    void verifyIsDone() {
        Task task = Task.builder()
                .id(1L)
                .title("title")
                .description("description")
                .state(State.DONE)
                .creationDate(LocalDate.now())
                .build();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(task));

        TaskException exception = assertThrows(TaskException.class, () -> service.modifyState(1L));

        assertEquals("task is already done", exception.getMessage());
    }

    @Test
    void verifyNotFoundTask() {

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(2L, Task.builder()
                .title("title")
                .description("description")
                .build()));

        assertEquals("task not found", exception.getMessage());
    }

    @Test
    void verifyIdNull() {
        TaskException exception = assertThrows(TaskException.class, () ->
                service.delete(null));
        assertEquals("id is null", exception.getMessage());
    }

    @Test
    void verifyTaskNull() {
        TaskException exception = assertThrows(TaskException.class, () ->
                service.update(1L, null));
        assertEquals("task is null", exception.getMessage());
    }

    @Test
    void verifyTitleNull() {
        TaskException exception = assertThrows(TaskException.class, () ->
                service.create(Task.builder()
                        .title(null)
                        .description("description")
                        .build())
        );
        assertEquals("title is required", exception.getMessage());
    }

    @Test
    void verifyDescriptionNull() {
        TaskException exception = assertThrows(TaskException.class, () ->
                service.create(Task.builder()
                        .title("title")
                        .description(null)
                        .build())
        );
        assertEquals("description is required", exception.getMessage());
    }

    @Test
    void verifyTitleIsEmpty() {
        TaskException exception = assertThrows(TaskException.class, () ->
                service.create(Task.builder()
                        .title("")
                        .description("description")
                        .build())
        );
        assertEquals("title is empty", exception.getMessage());
    }

    @Test
    void verifyDescriptionIsEmpty() {
        TaskException exception = assertThrows(TaskException.class, () ->
                service.create(Task.builder()
                        .title("title")
                        .description("")
                        .build())
        );
        assertEquals("description is empty", exception.getMessage());
    }
}
