package com.example.springbootproject.mapper;

import com.example.springbootproject.model.dto.AssignTeacher;
import com.example.springbootproject.model.dto.GradeWithStuWithCourse;
import com.example.springbootproject.model.dto.NotifyWithCourse;
import com.example.springbootproject.model.entity.*;
import org.apache.catalina.LifecycleState;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface MapperB {
    //根据userid得到教师信息
    @Select("select * from teachers where user_id = #{userId};")
    Teacher getTeacher(Integer userId);

    //添加老师信息
    @Insert("insert ignore into teachers (user_id) values (#{userId});")
    Integer addTe(Integer userId);

    //查询我的授课
    @Select("select * from courses c where c.teacher_id = #{tId};")
    List<Course> myTeach(Integer tId);

    //修改老师信息
    @Update("update teachers set name = #{name}, subject = #{subject}, experience = #{experience} where id = #{id}")
     void updateTe(Teacher teacher);

    //添加我的授课
    @Insert("insert into courses(title, teacher_id, start_time, end_time) VALUES (#{title},#{teacherId},#{startTime},#{endTime})")
    void addCourse(Course course);

    //删除授课
    @Delete("delete from courses where title = #{title} and teacher_id = #{teacherId}")
    void delCourse(@Param("title") String title,@Param("teacherId") Integer teacherId);

    //添加通知
    @Insert("insert into notifications (title, content, course_id) values (#{title},#{content},#{courseId});")
    void addNotify(Notify notify);

    //查询我的通知
    @Select("select n.* ,c.title as courseName from notifications n join courses c on n.course_id = c.id where c.teacher_id = #{tId};")
    List<NotifyWithCourse> getNotify(Integer tId);

    //删除通知
    @Delete("delete from notifications where id = #{Nid}")
    void delNotify(Integer Nid);

    //我的学生成绩
    @Select("select s.name as stuName,c.title as title,ce.student_id as stuId,ce.course_id as cId,g.grade as grade from courses c join course_enrollments ce on c.id = ce.course_id  join students s on ce.student_id = s.id left join grades g on c.id = g.course_id and g.student_id = s.id where c.teacher_id = #{tId}")
    List<GradeWithStuWithCourse> getGrade(Integer tId);

    //添加成绩
    @Insert("insert into grades (student_id, course_id, grade) values (#{studentId},#{courseId},#{grade})")
    void addGrade(Grade grade);

    //修改成绩
    @Update("update grades set grade = #{grade} where student_id = #{studentId} and course_id = #{courseId}")
    void updateGrade(Grade grade);

    //查询成绩
    @Select("select * from grades where student_id = #{studentId} and course_id = #{courseId}")
    Grade findGrade(Grade grade);

    //查看作业
    @Select("select a.id assignId,s.id stuId, s.name stuName,c.title cName,a.course_id cId,a.title assignName,a.description ,`as`.student_id stuId2, `as`.content,`as`.grade from assignments a join courses c on a.course_id = c.id join course_enrollments ce on a.course_id = ce.course_id join students s on ce.student_id = s.id left join assignments_submissions `as` on a.id = `as`.assignment_id and s.id = `as`.student_id where a.course_id in (select id from courses where teacher_id = #{tId})")
    List<AssignTeacher> getAssign(Integer tId);

    //添加作业提交表
    @Insert("insert into assignments_submissions(student_id, content, grade, assignment_id) values (#{studentId},#{content},#{grade},#{assignmentId})")
    void addAssignSubmit(AssignmentSubmission assignmentSubmission);

    //修改作业提交表
    @Update("update assignments_submissions set grade = #{grade} where student_id = #{studentId} and assignment_id = #{assignmentId};")
    void updateAssignSubmit(AssignmentSubmission assignmentSubmission);

    //添加作业表
    @Insert("insert into assignments (course_id, title, description) values (#{courseId},#{title},#{description})")
    void addAssign(Assignment assignment);
}
