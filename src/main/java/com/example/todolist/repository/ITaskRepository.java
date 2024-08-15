package com.example.todolist.repository;

import com.example.todolist.entity.Task;
import com.example.todolist.entity.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByState(State state);

}
