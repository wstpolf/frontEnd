package com.example.springbootproject.service.impl;

import com.example.springbootproject.mapper.MapperB;
import com.example.springbootproject.model.dto.AssignTeacher;
import com.example.springbootproject.model.dto.GradeWithStuWithCourse;
import com.example.springbootproject.model.dto.NotifyWithCourse;
import com.example.springbootproject.model.entity.*;
import com.example.springbootproject.service.TeacherService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private MapperB mapperB;
    //查找老师信息
    @Override
    public Teacher getTeacher(Integer userId) {
        return mapperB.getTeacher(userId);
    }
    //添加老师信息

    @Override
    public boolean addTe(Integer userId) {
        return mapperB.addTe(userId)==1;
    }
    //查询我的授课

    @Override
    public List<Course> myTeach(Integer tid) {
        return mapperB.myTeach(tid);
    }

    //修改教师信息

    @Override
    public void updateTe(Teacher teacher) {
        mapperB.updateTe(teacher);
    }

    @Override
    public void addCourse(Course course) {
        mapperB.addCourse(course);
    }

    @Override
    public void delCourse(String title, Integer teacherId) {
        mapperB.delCourse(title,teacherId);
    }

    @Override
    public void addNotify(Notify notify) {
        mapperB.addNotify(notify);
    }

    @Override
    public List<NotifyWithCourse> getNotify(Integer tId) {
        return mapperB.getNotify(tId);
    }

    @Override
    public void delNotify(Integer Nid) {
        mapperB.delNotify(Nid);
    }

    @Override
    public List<GradeWithStuWithCourse> getGrade(Integer tId) {
        return mapperB.getGrade(tId);
    }

    @Override
    public void opGrade(Grade grade) {
        if(mapperB.findGrade(grade)==null){
            mapperB.addGrade(grade);
        }else {
            mapperB.updateGrade(grade);
        }
    }

    @Override
    public List<AssignTeacher> getAssign(Integer tId) {
        return mapperB.getAssign(tId);
    }

    @Override
    public void opAssign(AssignmentSubmission assignmentSubmission,Integer notNull) {
        if(notNull==1){
            mapperB.updateAssignSubmit(assignmentSubmission);
        }else {
            mapperB.addAssignSubmit(assignmentSubmission);
        }
    }

    @Override
    public void addAssign(Assignment assignment) {
        mapperB.addAssign(assignment);
    }
}
