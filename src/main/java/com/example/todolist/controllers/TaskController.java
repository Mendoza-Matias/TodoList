package com.example.todolist.controllers;

import com.example.todolist.mapper.TaskMapper;
import com.example.todolist.model.TaskDto;
import com.example.todolist.services.ITaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TaskController implements TaskApi {

    @Autowired
    private ITaskServices services;

    @Autowired
    private TaskMapper mapper;


    @Override
    public ResponseEntity<Void> deleteTask(Long idTask) {
        services.delete(idTask);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok().body(mapper.mapList(services.getAll()));
    }

    @Override
    public ResponseEntity<List<TaskDto>> getTasksByStateFilter(String state) {
        return ResponseEntity.ok().body(mapper.mapList(services.getByStateFilter(state)));
    }

    @Override
    public ResponseEntity<Void> patchModifyTaskState(Long idTask) {
        services.modifyState(idTask);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> postCreateNewTask(TaskDto taskDto) {
        services.create(mapper.unmap(taskDto));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> putModifyInformationTask(Long idTask, TaskDto taskDto) {
        services.update(idTask, mapper.unmap(taskDto));
        return ResponseEntity.ok().build();
    }
}
