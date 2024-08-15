package com.example.todolist.services.impl;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.enums.State;
import com.example.todolist.entity.exceptions.NotFoundException;
import com.example.todolist.entity.exceptions.TaskException;
import com.example.todolist.repository.ITaskRepository;
import com.example.todolist.services.ITaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements ITaskServices {

    @Autowired
    private ITaskRepository repository;


    @Override
    public List<Task> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Task> getByStateFilter(String state) {
        if (state.equals("IN_PROGRESS"))
            return repository.findAllByState(State.IN_PROGRESS);
        if (state.equals("DONE"))
            return repository.findAllByState(State.DONE);
        return repository.findAll();
    }

    @Override
    public void create(Task task) {
        validateFields(task);
        repository.save(Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .state(State.IN_PROGRESS)
                .build());
    }

    @Override
    public void modifyState(Long id) {
        this.validateId(id);
        Task task = repository.findById(id).orElseThrow(() -> new NotFoundException("task not found"));

        if (task.getState().equals(State.DONE))
            throw new TaskException("task is already done");

        repository.deleteById(id);
        repository.save(
                Task.builder()
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .state(State.DONE)
                        .build()
        );
    }

    @Override
    public void update(Long id, Task task) {

        this.validateId(id);

        this.validateTask(task);

        Task taskNotFound = repository.findById(id).orElseThrow(() -> new NotFoundException("task not found"));

        if (taskNotFound.getState().equals(State.DONE))
            throw new TaskException("task is already done");

        this.validateFields(task);

        repository.deleteById(id);

        repository.save(Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .state(State.IN_PROGRESS)
                .build());
    }

    @Override
    public void delete(Long id) {
        this.validateId(id);
        repository.deleteById(id);
    }

    private void validateTask(Task task) {
        if (task == null)
            throw new TaskException("task is null");
    }

    private void validateId(Long id) {
        if (id == null)
            throw new TaskException("id is null");
    }

    private void validateFields(Task task) {
        if (task.getTitle() == null)
            throw new TaskException("title is required");
        if (task.getTitle().isEmpty())
            throw new TaskException("title is empty");
        if (task.getDescription() == null)
            throw new TaskException("description is required");
        if (task.getDescription().isEmpty())
            throw new TaskException("description is empty");
    }
}
