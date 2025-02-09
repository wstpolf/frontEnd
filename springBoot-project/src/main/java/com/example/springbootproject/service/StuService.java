package com.example.springbootproject.service;

import com.example.springbootproject.model.dto.*;
import com.example.springbootproject.model.entity.AssignmentSubmission;
import com.example.springbootproject.model.entity.Course;
import com.example.springbootproject.model.entity.Student;
import com.example.springbootproject.model.entity.Teacher;

import java.util.List;

public interface StuService {
    public List<CourseWithTeacher> getAllCourse(Integer stuId);
    public void deleteCourseById(Integer id);
    public void addCourse(Course course);
    public void updateCourse(Integer id, Course course);
    UserResponse login(String username, String password);
    List<CourseWithTeacher> getMyCourse(Integer stuId);
    Student getStuInform(Integer userId);
    boolean addUser(String username, String password,String role,String phone);
    boolean delEnroll(Integer courseId, Integer stuId);
    boolean addEnroll(Integer stuId, Integer courseId);
    List<CourseWithGrade> getMyGrade(Integer stuId);
    List<AssignWithCourse> getMyAssign(Integer stuId);
    void addAssignSubmission(AssignmentSubmission assignmentSubmission);
    void addStu(Integer userId);
    void updateStu(Student student);
    List<NotifyWithCourse> getNotify(Integer stuId);

}
