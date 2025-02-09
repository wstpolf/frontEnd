package com.example.springbootproject.controller;

import com.example.springbootproject.model.dto.AssignTeacher;
import com.example.springbootproject.model.dto.GradeWithStuWithCourse;
import com.example.springbootproject.model.dto.NotifyWithCourse;
import com.example.springbootproject.model.entity.*;
import com.example.springbootproject.service.TeacherService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.geom.RectangularShape;
import java.util.List;

@RestController
@RequestMapping("/te")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    //查询教师信息
    @GetMapping("/myInform/{userId}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable Integer userId){
        return ResponseEntity.ok(teacherService.getTeacher(userId));
    }
    //新增教师信息
    @PostMapping("/addTe/{userId}")
    public ResponseEntity<Boolean> addTe(@PathVariable Integer userId){
        return ResponseEntity.ok(teacherService.addTe(userId));
    }

    //查找老师授课
    @GetMapping("/myTeach/{tid}")
    public ResponseEntity<List<Course>> myTeach(@PathVariable Integer tid){
        return  ResponseEntity.ok(teacherService.myTeach(tid));
    }

    //修改教师信息
    @PutMapping("/updateTe")
    public ResponseEntity<String> updateInform(@RequestBody Teacher teacher){
        teacherService.updateTe(teacher);
        return ResponseEntity.ok("success");
    }

    //添加授课信息
    @PostMapping("/addCourse")
    public ResponseEntity<String> addCourse(@RequestBody Course course){
        teacherService.addCourse(course);
        return ResponseEntity.ok("success");
    }

    //删除授课信息
    @DeleteMapping("/delCourse")
    public ResponseEntity<String> delCourse(@RequestParam String title, @RequestParam Integer teacherId){
        teacherService.delCourse(title,teacherId);
        return ResponseEntity.ok("success");
    }

    //发布通知
    @PostMapping("/addNotify")
    public ResponseEntity<String> addNotify(@RequestBody Notify notify){
        teacherService.addNotify(notify);
        return ResponseEntity.ok("success");
    }

    //显示通知
    @GetMapping("/getNotify/{tId}")
    public ResponseEntity<List<NotifyWithCourse>> getNotify(@PathVariable Integer tId){
        return ResponseEntity.ok(teacherService.getNotify(tId));
    }

    //删除通知
    @DeleteMapping("/delNotify/{Nid}")
    ResponseEntity<String> delNotify(@PathVariable Integer Nid){
        teacherService.delNotify(Nid);
        return ResponseEntity.ok("success");
    }

    //取得成绩
    @GetMapping("/getGrade/{tId}")
    ResponseEntity<List<GradeWithStuWithCourse>> getGrade(@PathVariable Integer tId){
        return ResponseEntity.ok(teacherService.getGrade(tId));
    }

    //打成绩
    @PostMapping("/opGrade")
    ResponseEntity<String> opGrade(@RequestBody Grade grade){
        teacherService.opGrade(grade);
        return ResponseEntity.ok("success");
    }

    //看作业
    @GetMapping("/getAssign/{tId}")
    ResponseEntity<List<AssignTeacher>> getAssign(@PathVariable Integer tId){
        return ResponseEntity.ok(teacherService.getAssign(tId));
    }

    //操作作业
    @PostMapping("/opAssign/{stuId}")
    ResponseEntity<String> opAssignSubmit(@PathVariable Integer stuId, @RequestBody AssignmentSubmission assignmentSubmission){
        teacherService.opAssign(assignmentSubmission,stuId);
        return ResponseEntity.ok("success");
    }

    //发布作业
    @PostMapping("/addAssign")
    ResponseEntity<String> addAssign(@RequestBody Assignment assignment){
        teacherService.addAssign(assignment);
        return ResponseEntity.ok("success");
    }
}
