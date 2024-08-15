package com.example.todolist.services.impl;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.enums.State;
import com.example.todolist.repository.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl service;

    @Mock
    private ITaskRepository repository;

    private Task task;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1L)
                .title("title")
                .description("description")
                .creationDate(LocalDate.now())
                .state(State.IN_PROGRESS)
                .build();
    }

    @Test
    void deleteTask() {
        Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());

        service.delete(1L);

        Mockito.verify(repository).deleteById(Mockito.anyLong());
    }

    @Test
    void getAllTask() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(task));

        List<Task> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("title", result.get(0).getTitle());
        assertEquals("description", result.get(0).getDescription());
        assertEquals("IN_PROGRESS", result.get(0).getState().name());
    }

    @Test
    void getTasksByEmptyState() {

        Mockito.when(repository.findAll()).thenReturn(
                Arrays.asList(
                        Task.builder()
                                .id(1L)
                                .title("title")
                                .description("description")
                                .creationDate(LocalDate.now())
                                .state(State.IN_PROGRESS)
                                .build()
                        ,
                        Task.builder()
                                .id(1L)
                                .title("title")
                                .description("description")
                                .creationDate(LocalDate.now())
                                .state(State.DONE)
                                .build()
                )
        );

        List<Task> result = service.getByStateFilter("");

        assertEquals(2, result.size());
        assertEquals("IN_PROGRESS", result.get(0).getState().name());
        assertEquals("DONE", result.get(1).getState().name());
    }

    @Test
    void getTasksByStateInProgress() {

        ArgumentCaptor<State> captor = ArgumentCaptor.forClass(State.class);

        Mockito.when(repository.findAllByState(captor.capture())).thenReturn(Arrays.asList(
                Task.builder()
                        .id(1L)
                        .title("title")
                        .description("description")
                        .creationDate(LocalDate.now())
                        .state(State.IN_PROGRESS)
                        .build()));

        List<Task> result = service.getByStateFilter("in_progress");

        assertEquals("IN_PROGRESS", captor.getValue().name());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("title", result.get(0).getTitle());
        assertEquals("description", result.get(0).getDescription());
        assertEquals("IN_PROGRESS", result.get(0).getState().name());
    }

    @Test
    void getTasksByStateDone() {

        ArgumentCaptor<State> captor = ArgumentCaptor.forClass(State.class);

        Mockito.when(repository.findAllByState(captor.capture())).thenReturn(Arrays.asList(
                Task.builder()
                        .id(3L)
                        .title("title")
                        .description("description")
                        .creationDate(LocalDate.now())
                        .state(State.DONE)
                        .build()
        ));

        List<Task> result = service.getByStateFilter("done");

        assertEquals("DONE", captor.getValue().name());
        assertEquals(1, result.size());
        assertEquals(3L, result.get(0).getId());
        assertEquals("title", result.get(0).getTitle());
        assertEquals("description", result.get(0).getDescription());
        assertEquals("DONE", result.get(0).getState().name());
    }

    @Test
    void pathModifyTaskState() {
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        ArgumentCaptor<Long> captorId = ArgumentCaptor.forClass(Long.class);

        Task task = Task.builder()
                .id(1L)
                .title("title")
                .description("description")
                .state(State.IN_PROGRESS)
                .creationDate(LocalDate.now())
                .build();

        Mockito.when(repository.findById(captorId.capture())).thenReturn(Optional.of(task));
        Mockito.doNothing().when(repository).deleteById(captorId.capture());
        Mockito.when(repository.save(captor.capture())).thenReturn(task);

        service.modifyState(1L);

        Mockito.verify(repository).findById(Mockito.anyLong());
        Mockito.verify(repository).deleteById(Mockito.anyLong());
        Mockito.verify(repository).save(Mockito.any(Task.class));

        assertEquals("title", captor.getValue().getTitle());
        assertEquals("description", captor.getValue().getDescription());
        assertEquals("DONE", captor.getValue().getState().name());
    }

    @Test
    void postCreateNewTask() {
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        Mockito.when(repository.save(captor.capture())).thenReturn(Mockito.any(Task.class));
        service.create(Task.
                builder()
                .title("title")
                .description("description")
                .build());
        Mockito.verify(repository).save(Mockito.any(Task.class));

        assertEquals("title", captor.getValue().getTitle());
        assertEquals("description", captor.getValue().getDescription());
    }

    @Test
    void updateTask() {

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        ArgumentCaptor<Long> captorId = ArgumentCaptor.forClass(Long.class);

        Task task = Task.builder()
                .id(2L)
                .title("new title")
                .description("new description")
                .state(State.IN_PROGRESS)
                .creationDate(LocalDate.now())
                .build();

        Mockito.when(repository.findById(captorId.capture())).thenReturn(Optional.ofNullable(task));

        Mockito.doNothing().when(repository).deleteById(captorId.capture());

        Mockito.when(repository.save(captor.capture())).thenReturn(task);

        service.update(2L, Task.builder()
                .title("new title")
                .description("new description")
                .build());

        Mockito.verify(repository).findById(Mockito.anyLong());
        Mockito.verify(repository).deleteById(Mockito.anyLong());
        Mockito.verify(repository).save(Mockito.any(Task.class));

        assertEquals(2L, captorId.getValue());
        assertEquals("new title", captor.getValue().getTitle());
        assertEquals("new description", captor.getValue().getDescription());
    }
}