package com.example.springbootproject.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Assignment {
    private Integer id;
    private Integer courseId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
