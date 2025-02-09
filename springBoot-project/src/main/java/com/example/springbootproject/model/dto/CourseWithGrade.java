package com.example.springbootproject.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CourseWithGrade {
    private String title;  // 课程标题
    private Integer courseId;    // 课程ID
    private BigDecimal grade;    // 成绩，精度为5位，小数点后2位
}
