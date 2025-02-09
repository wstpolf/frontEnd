package com.example.springbootproject.service.impl;

import com.example.springbootproject.mapper.MapperA;
import com.example.springbootproject.model.dto.*;
import com.example.springbootproject.model.entity.AssignmentSubmission;
import com.example.springbootproject.model.entity.Course;
import com.example.springbootproject.model.entity.Student;
import com.example.springbootproject.model.entity.Teacher;
import com.example.springbootproject.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private MapperA mapperA;


    //我的课程

    @Override
    public List<NotifyWithCourse> getNotify(Integer stuId) {
        return mapperA.getNotify(stuId);
    }

    //修改学生信息
    @Override
    public void updateStu(Student student) {
        mapperA.updateStu(student);
    }

    //添加学生信息

    @Override
    public void addStu(Integer userId) {
        mapperA.addStu(userId);
    }

    //查看作业

    @Override
    public void addAssignSubmission(AssignmentSubmission assignmentSubmission) {
        mapperA.addAssignSubmission(assignmentSubmission);
    }

    @Override
    public List<AssignWithCourse> getMyAssign(Integer stuId) {
        List<AssignWithCourse> assignWithCourses = mapperA.getMyAssign(stuId);
        for (AssignWithCourse assignWithCourse : assignWithCourses) {
            if (Objects.equals(assignWithCourse.getContent(), null)){
                assignWithCourse.setStatus("未提交");
            }else {
                assignWithCourse.setStatus("已提交");
            }
        }
        return assignWithCourses;
    }

    //查询成绩
    @Override
    public List<CourseWithGrade> getMyGrade(Integer stuId) {
        return mapperA.getMyGrade(stuId);
    }

    //选课，添加报名信息
    @Override
    public boolean addEnroll(Integer stuId, Integer courseId) {
        return (mapperA.addEnroll(stuId,courseId)==1);
    }

    //退课，删除报名信息
    @Override
    public boolean delEnroll(Integer courseId, Integer stuId) {
        return (mapperA.delEnroll(courseId,stuId)==1);
    }

    /**
     * 获取所有课程信息
     * @return 所有课程信息
     */
    @Override
    public List<CourseWithTeacher> getAllCourse(Integer stuId) {
        return mapperA.listCourse( stuId);
    }
    /**
     * delete course by id
     *
     * @return
     */
    @Override
    public void deleteCourseById(Integer id){
        mapperA.deleteCourseById(id);
    }
    /**
     * add course
     */
    @Override
    public void addCourse(Course course) {
        mapperA.addCourse(course);
    }

    /**
     * update course
     * @param id
     * @param course
     */
    @Override
    public void updateCourse(Integer id, Course course) {
        course.setId(id);
        mapperA.update(course);
    }

    //登录获得用户信息
    @Override
    public UserResponse login(String username, String password) {
        return mapperA.getUser(username,password);
    }


    //获取我报名的课程
    @Override
    public List<CourseWithTeacher> getMyCourse(Integer stuId) {
        return mapperA.listMyCourse(stuId);
    }

    //获取学生信息

    @Override
    public Student getStuInform(Integer userId) {
        return mapperA.getStuInform(userId);
    }
    //添加用户

    @Override
    public boolean addUser(String username, String password,String role,String phone) {
        return  (mapperA.addUser(username,password,role,phone)==1)?true:false;
    }


}
