package com.example.springbootproject.model.entity;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CourseEnrollment {
    private Integer id;          // 主键，自增ID
    private Integer studentId;   // 学生ID
    private Integer courseId;    // 课程ID
    private LocalDate createdAt; // 创建时间，默认为当前时间
}
