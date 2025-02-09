package com.example.springbootproject.service;

import com.example.springbootproject.model.dto.AssignTeacher;
import com.example.springbootproject.model.dto.GradeWithStuWithCourse;
import com.example.springbootproject.model.dto.NotifyWithCourse;
import com.example.springbootproject.model.entity.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TeacherService {
    //获取老师信息
    Teacher getTeacher(Integer userId);
    //添加老师信息
    boolean addTe(Integer userId);
    //查询我的授课
    List<Course> myTeach(Integer tid);
    //修改教师信息
    void updateTe(Teacher teacher);
    //创建我的授课
    void addCourse(Course course);
    //删除授课
    void delCourse(String title, Integer teacherId);
    //发布通知
    void addNotify(Notify notify);
    //查询通知
    List<NotifyWithCourse> getNotify(Integer tId);
    //删除通知
    void delNotify(Integer Nid);
    //看成绩
    List<GradeWithStuWithCourse> getGrade(Integer tId);
    //操作成绩
    void opGrade(Grade grade);
    //查看作业
    List<AssignTeacher> getAssign(Integer tId);
    //操作作业
    void opAssign(AssignmentSubmission assignmentSubmission,Integer notNull);
    //发布作业
    void addAssign(Assignment assignment);
}
