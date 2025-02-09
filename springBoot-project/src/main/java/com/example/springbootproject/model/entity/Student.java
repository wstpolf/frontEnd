package com.example.springbootproject.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Student {
    private Integer id;
    private Integer userId;
    private String name;
    private String grade;
    private String parentPhone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
