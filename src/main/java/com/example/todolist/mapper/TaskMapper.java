package com.example.todolist.mapper;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.enums.State;
import com.example.todolist.model.TaskDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskMapper extends IGenericMapper<Task, TaskDto> {
    @Override
    public TaskDto map(Task entity) {
        return new TaskDto()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .state(TaskDto.StateEnum.valueOf(entity.getState().name()))
                .creationDate(entity.getCreationDate());
    }

    @Override
    public Task unmap(TaskDto dto) {
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .state(dto.getState() == null ? null : State.valueOf(dto.getState().name()))
                .creationDate(dto.getCreationDate())
                .build();
    }
}
