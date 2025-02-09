package com.example.springbootproject.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Grade {
    private Integer id;          // 主键，自增ID
    private Integer studentId;   // 学生ID
    private Integer courseId;    // 课程ID
    private BigDecimal grade;    // 成绩，精度为5位，小数点后2位
    private LocalDateTime createdAt; // 创建时间，默认为当前时间
}
