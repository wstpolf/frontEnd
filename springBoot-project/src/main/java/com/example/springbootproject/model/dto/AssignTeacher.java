package com.example.springbootproject.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssignTeacher {
    private Integer assignId;
    private Integer stuId;
    private String stuName;
    private String cName;
    private Integer cId;
    private String assignName;
    private String description;
    private Integer stuId2;
    private String content;
    private BigDecimal grade;
}
