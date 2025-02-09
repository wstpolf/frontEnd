package com.example.springbootproject.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notify {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Integer courseId;
}
