package com.example.springbootproject.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GradeWithStuWithCourse {
    private String stuName;
    private String title;
    private Integer stuId;
    private Integer cId;
    private BigDecimal grade;
}
