package com.example.todolist.mapper;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.enums.State;
import com.example.todolist.model.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskMapperTest {

    @InjectMocks
    private TaskMapper taskMapper;

    private Task task;

    private TaskDto dto;

    @BeforeEach
    void setUp() {
        dto = new TaskDto()
                .id(1L)
                .title("title")
                .description("description")
                .creationDate(LocalDate.now())
                .state(TaskDto.StateEnum.DONE);

        task = Task
                .builder()
                .id(1L)
                .title("title")
                .description("description")
                .creationDate(LocalDate.now())
                .state(State.DONE)
                .build();
    }

    @Test
    void map() {
        TaskDto result = taskMapper.map(task);

        assertEquals(1L, result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("description", result.getDescription());
        assertEquals(LocalDate.now(), result.getCreationDate());
        assertEquals("DONE", result.getState().name());
    }

    @Test
    void mapList() {
        List<TaskDto> result = taskMapper.mapList(Arrays.asList(task));

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("title", result.get(0).getTitle());
        assertEquals("description", result.get(0).getDescription());
        assertEquals(LocalDate.now(), result.get(0).getCreationDate());
        assertEquals("DONE", result.get(0).getState().name());
    }

    @Test
    void unmap() {
        Task result = taskMapper.unmap(dto);

        assertEquals(1L, result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("description", result.getDescription());
        assertEquals(LocalDate.now(), result.getCreationDate());
        assertEquals("DONE", result.getState().name());
    }

    @Test
    void unmapList() {
        List<Task> result = taskMapper.unMapList(Arrays.asList(dto));

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("title", result.get(0).getTitle());
        assertEquals("description", result.get(0).getDescription());
        assertEquals(LocalDate.now(), result.get(0).getCreationDate());
        assertEquals("DONE", result.get(0).getState().name());
    }
}