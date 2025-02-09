package com.example.springbootproject.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AssignmentSubmission {
    private Integer id;
    private Integer studentId;
    private String content;
    private BigDecimal grade;
    private LocalDateTime createdAt;
    private Integer assignmentId;
}
