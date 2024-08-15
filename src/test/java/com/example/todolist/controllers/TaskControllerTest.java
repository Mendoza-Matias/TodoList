package com.example.todolist.controllers;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.enums.State;
import com.example.todolist.mapper.TaskMapper;
import com.example.todolist.model.TaskDto;
import com.example.todolist.services.ITaskServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskMapper mapper;

    @Mock
    private ITaskServices services;

    @Test
    void verifyDeleteArguments() {

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        Mockito.doNothing().when(services).delete(captor.capture());

        taskController.deleteTask(1L);

        assertEquals(1L, captor.getValue());
    }

    @Test
    void verifyGetAllTasksResponse() {
        Mockito.when(services.getAll()).thenReturn(Arrays.asList(new Task()));

        Mockito.when(mapper.mapList(Mockito.anyList())).thenReturn(
                Arrays.asList(
                        new TaskDto()
                                .id(1L)
                                .title("title")
                                .description("description")
                                .creationDate(LocalDate.now())
                                .state(TaskDto.StateEnum.DONE)
                )
        );

        ResponseEntity<List<TaskDto>> response = taskController.getAllTasks();

        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("title", response.getBody().get(0).getTitle());
        assertEquals("description", response.getBody().get(0).getDescription());
        assertEquals("DONE", response.getBody().get(0).getState().name());
        assertEquals(LocalDate.now(), response.getBody().get(0).getCreationDate());
    }

    @Test
    void verifyGetTasksByStateFilterArguments() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        Mockito.when(services.getByStateFilter(captor.capture())).thenReturn(Arrays.asList());

        taskController.getTasksByStateFilter("done");

        assertEquals("done", captor.getValue());
    }

    @Test
    void verifyGetTasksByStateFilterResponse() {

        Mockito.when(services.getByStateFilter(Mockito.anyString())).thenReturn(Arrays.asList(new Task()));
        Mockito.when(mapper.mapList(Mockito.anyList())).thenReturn(Arrays.asList(
                new TaskDto()
                        .id(1L)
                        .title("title")
                        .description("description")
                        .creationDate(LocalDate.now())
                        .state(TaskDto.StateEnum.DONE)
        ));
        ResponseEntity<List<TaskDto>> response = taskController.getTasksByStateFilter("done");

        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("title", response.getBody().get(0).getTitle());
        assertEquals("description", response.getBody().get(0).getDescription());
        assertEquals("DONE", response.getBody().get(0).getState().name());
        assertEquals(LocalDate.now(), response.getBody().get(0).getCreationDate());
    }

    @Test
    void verifyPatchModifyTaskStateArguments() {

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        Mockito.doNothing().when(services).modifyState(captor.capture());

        taskController.patchModifyTaskState(1L);

        assertEquals(1L, captor.getValue());
    }

    @Test
    void verifyPostCreateNewTaskArguments() {
        ArgumentCaptor<Task> captorTask = ArgumentCaptor.forClass(Task.class);

        Mockito.doNothing().when(services).create(captorTask.capture());
        Mockito.when(mapper.unmap(Mockito.any(TaskDto.class))).thenReturn(
                Task.builder()
                        .title("title")
                        .description("description")
                        .build()
        );

        taskController.postCreateNewTask(new TaskDto()
                .title("title")
                .description("description"));

        assertEquals("title", captorTask.getValue().getTitle());
        assertEquals("description", captorTask.getValue().getDescription());
    }

    @Test
    void putModifyInformationTask() {
        ArgumentCaptor<Task> captorTask = ArgumentCaptor.forClass(Task.class);
        ArgumentCaptor<Long> captorId = ArgumentCaptor.forClass(Long.class);

        Mockito.doNothing().when(services).update(captorId.capture(), captorTask.capture());
        Mockito.when(mapper.unmap(Mockito.any(TaskDto.class))).thenReturn(
                Task.builder()
                        .title("title")
                        .description("description")
                        .build()
        );

        taskController.putModifyInformationTask(1L, new TaskDto()
                .title("title")
                .description("description"));

        assertEquals(1L, captorId.getValue());
        assertEquals("title", captorTask.getValue().getTitle());
        assertEquals("description", captorTask.getValue().getDescription());
    }
}