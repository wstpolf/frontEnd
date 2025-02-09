package com.example.springbootproject.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyWithCourse {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Integer courseId;
    private String courseName;
}
