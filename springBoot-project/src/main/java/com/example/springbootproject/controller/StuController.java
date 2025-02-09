package com.example.springbootproject.controller;

import com.example.springbootproject.model.dto.*;
import com.example.springbootproject.model.entity.*;
import com.example.springbootproject.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stu")
public class StuController {
    @Autowired
    private StuService stuService;

    //添加学生信息
    @PostMapping("/addStu/{userId}")
    ResponseEntity<String> addStu(@PathVariable Integer userId){
        stuService.addStu(userId);
        return ResponseEntity.ok("成功");
    }

    //修改学生信息
    @PutMapping("/updateStu")
    ResponseEntity<String > updateStu(@RequestBody Student student){
        stuService.updateStu(student);
        return ResponseEntity.ok("成功");
    }
    //提交作业
    @PostMapping("/submitAssign")
    ResponseEntity<String> submitAssign(@RequestBody AssignmentSubmission assignmentSubmission){
        stuService.addAssignSubmission(assignmentSubmission);
        return ResponseEntity.ok("成功");
    }
    //学生看作业
    @GetMapping("/myAssign/{stuId}")
    ResponseEntity<List<AssignWithCourse>> getMyAssign(@PathVariable Integer stuId){
        return ResponseEntity.ok(stuService.getMyAssign(stuId));
    }
    //学生查询成绩
    @GetMapping("/myGrade/{stuId}")
    public ResponseEntity<List<CourseWithGrade>> getMyGrade(@PathVariable Integer stuId){
        return ResponseEntity.ok(stuService.getMyGrade(stuId));
    }
    //获取所有课程信息
    @GetMapping("/allCourse/{stuId}")
    public ResponseEntity<List<CourseWithTeacher>> getAllCourse(@PathVariable Integer stuId){
        return ResponseEntity.ok(stuService.getAllCourse(stuId));
    }

    //添加选课信息
    @PostMapping("/selectCourse")
    public ResponseEntity<String> selectCourse(@RequestBody CourseEnrollment courseEnrollment){
        return ResponseEntity.ok(stuService.addEnroll(courseEnrollment.getStudentId(),courseEnrollment.getCourseId())?"选课成功":"选课失败");
    }
    //验证登录信息
    @GetMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestParam String username,@RequestParam String password){
        UserResponse userResponse = stuService.login(username,password);
        return ResponseEntity.ok(userResponse);
    }

    //根据学生id查找她的课程
    @GetMapping("/myClass/{id}")
    public ResponseEntity<List<CourseWithTeacher>> getMyClass(@PathVariable Integer id){
        return ResponseEntity.ok(stuService.getMyCourse(id));
    }

    //获取学生信息by userid
    @GetMapping("/stuInform/{userId}")
    public ResponseEntity<Student> getStuInform(@PathVariable Integer userId){
        return ResponseEntity.ok(stuService.getStuInform(userId));
    }

    //注册新用户
    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody User user){
        if(stuService.addUser(user.getUsername(),user.getPassword(),user.getRole(),user.getPhone())){
            return ResponseEntity.ok("注册成功");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败：");
        }
    }

    //退课（删除学生报名信息）
    @DeleteMapping("/notEnroll/{courseId}/{stuId}")
    public ResponseEntity<String> deleteEnrollById(@PathVariable Integer courseId,@PathVariable Integer stuId){
        if(stuService.delEnroll( courseId, stuId)){
            return ResponseEntity.ok("退课成功");
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("退课失败");
        }
    }

    //查看通知
    @GetMapping("/notify/{stuId}")
    public ResponseEntity<List<NotifyWithCourse>> getNotify(@PathVariable Integer stuId){
        return ResponseEntity.ok(stuService.getNotify(stuId));
    }
//    //删除课程byid
//    @DeleteMapping("/delCourseById/{id}")
//    public ResponseEntity<String> deleteCourseById(@PathVariable Integer id){
//        try{
//            stuService.deleteCourseById(id);
//            return ResponseEntity.ok("删除成功");
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败：");
//        }
//    }
//    //添加课程
//    @PostMapping("/addCourse")
//    public ResponseEntity<String> addCourse(@RequestBody Course course){
//        try {
//            stuService.addCourse(course);  // 调用 service 层添加课程
//            return ResponseEntity.ok("课程添加成功");
//        } catch (Exception e) {
//            System.out.println("course:"+course);
//            System.out.println("e:"+e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("课程添加失败");
//        }
//    }
//    //更新课程
//    @PutMapping("/updateCourse/{id}")
//    public ResponseEntity<String> updateCourse(@RequestBody Course course,@PathVariable Integer id){
//        stuService.updateCourse(id,course);
//        return ResponseEntity.ok("update success!");
//    }
}
