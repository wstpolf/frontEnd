package com.example.springbootproject.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Teacher {
    private Integer id;
    private Integer userId;
    private String name;
    private String subject;
    private Integer experience;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
