package com.example.todolist.entity;

import com.example.todolist.entity.enums.State;
import com.example.todolist.model.TaskDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "task")

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private State state;

    @CreationTimestamp
    private LocalDate creationDate;
}
