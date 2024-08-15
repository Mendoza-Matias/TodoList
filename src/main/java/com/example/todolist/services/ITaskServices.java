package com.example.todolist.services;

import com.example.todolist.entity.Task;

import java.util.List;

public interface ITaskServices {

    List<Task> getAll();

    List<Task> getByStateFilter(String state);

    void create(Task task);

    void modifyState(Long id);

    void update(Long id, Task task);

    void delete(Long id);
}
