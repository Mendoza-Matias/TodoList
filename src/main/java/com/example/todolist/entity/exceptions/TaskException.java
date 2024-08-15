package com.example.todolist.entity.exceptions;

public class TaskException extends RuntimeException{
    public TaskException(String message) {
        super(message);
    }
}
