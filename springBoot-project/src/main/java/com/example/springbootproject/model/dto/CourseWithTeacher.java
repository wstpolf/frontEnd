package com.example.springbootproject.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourseWithTeacher {

    private Integer id;  // 自增长的主键
    private String title;  // 课程标题
    private Integer teacherId;  // 教师ID
    private LocalDate startTime;  // 开始时间
    private LocalDate endTime;  // 结束时间
    private String name;
    private String subject;
    private Integer experience;

}
