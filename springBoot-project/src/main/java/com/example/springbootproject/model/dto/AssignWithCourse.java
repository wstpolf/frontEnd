package com.example.springbootproject.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssignWithCourse {
    private Integer assignmentId;
    private String title; //作业标题
    private String courseTitle; //课程标题
    private String description;  //作业描述
    private String content; //回答内容
    private BigDecimal grade; //作业成绩
    private String status;//是否提交
}
