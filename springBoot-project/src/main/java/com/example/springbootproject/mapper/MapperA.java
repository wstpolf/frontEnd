package com.example.springbootproject.mapper;

import com.example.springbootproject.model.dto.*;
import com.example.springbootproject.model.entity.AssignmentSubmission;
import com.example.springbootproject.model.entity.Course;
import com.example.springbootproject.model.entity.Student;
import com.example.springbootproject.model.entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Mapper
public interface MapperA {
//    /**查询数据表所有数据
//     *
//     * @return 返回所有课程信息
//     */
//    @Select("select * from courses ")
//    public List<Course> listCourse();


    /**
     * delete course by id
     * @param id
     */
    @Delete("delete from courses where id = #{id}")
    public void deleteCourseById(Integer id);




    //查询作业信息
    @Select("SELECT a.id as assignmentId,a.title as title,c.title as courseTitle,a.description as description, `as`.content as content,`as`.grade as grade from assignments_submissions `as`  right join assignments a on `as`.assignment_id = a.id and as.student_id = #{stuId} join courses c on c.id = a.course_id where a.course_id in (select ce.course_id from course_enrollments ce where ce.student_id = #{stuId} )")
    List<AssignWithCourse> getMyAssign(Integer stuId);
    //查询学生成绩
    @Select("SELECT c.title AS title, " +
            "       c.id AS courseId, " +
            "       g.grade AS grade " +
            "FROM grades g " +
            "JOIN courses c ON g.course_id = c.id " +
            "WHERE g.student_id = #{studentId}")
    List<CourseWithGrade> getMyGrade(@Param("studentId") Integer studentId);

    //添加选课信息
    @Insert("insert into course_enrollments(student_id, course_id) values (#{studentId},#{courseId})")
    Integer addEnroll(@Param("studentId") Integer stuId,@Param("courseId") Integer courseId);

    @Insert("insert into students(user_id) values (#{userId}) ")
    void addStu(Integer userId);

    //删除选课表数据
    @Delete("delete from course_enrollments where course_id = #{courseId} and student_id = #{stuId}")
    Integer delEnroll(@Param("courseId") Integer courId, @Param("stuId") Integer stuId);

    /**
     *add course
     */
    @Insert("insert into courses(title, teacher_id, start_time, end_time) " +
            "values(#{title}, #{teacherId}, #{startTime}, #{endTime}) ")
    public void addCourse(Course course);

    //提交作业，添加课程提交表
    @Insert("insert into assignments_submissions (student_id, content, assignment_id) values (#{studentId},#{content},#{assignmentId});")
    void addAssignSubmission(AssignmentSubmission assignmentSubmission);

    /**
     * update course
     * @param course
     */
    @Update("update courses set title = #{title},teacher_id = #{teacherId},start_time = #{startTime},end_time = #{endTime} where id = #{id}")
    public void update(Course course);

    //修改学生信息
    @Update("update students set name = #{name},grade=#{grade},parent_phone=#{parentPhone} where id = #{id}")
    void updateStu(Student student);
    /**
     * select allCourse
     * @return
     */
    @Select("SELECT c.id, c.title, c.teacher_id, c.start_time, c.end_time, " +
            "t.name, t.subject, t.experience " +
            "FROM courses c " +
            "JOIN teachers t ON c.teacher_id = t.id where c.id not in(select course_id from course_enrollments where student_id = #{stuId})")
    List<CourseWithTeacher> listCourse(Integer stuId);

    //根据学生id查找报名表join course表
    @Select("SELECT c.id, c.title, c.teacher_id, c.start_time, c.end_time, " +
            "t.name, t.subject, t.experience " +
            "FROM courses c " +
            "JOIN teachers t ON c.teacher_id = t.id join course_enrollments ce on ce.course_id = c.id where ce.student_id = #{stuId} ")
    List<CourseWithTeacher> listMyCourse(Integer stuId);
    /**
     * 登录获取用户信息
     * @param username
     * @param password
     * @return
     */
    @Select("SELECT u.id,u.username,u.role,u.phone FROM users u where username = #{username} AND password = #{password}" )
    UserResponse getUser(@Param("username") String username,@Param("password") String password);

    //查看我的通知
    @Select("select n.*,c.title as course_name from notifications n join courses c on n.course_id = c.id join course_enrollments ce on n.course_id = ce.course_id where student_id = #{stuId} order by n.created_at desc")
    List<NotifyWithCourse> getNotify(Integer stuId);

    @Select("select * from students where students.user_id = #{userId}")
    Student getStuInform(Integer userId);
//注册
    @Insert("INSERT IGNORE INTO users(username, password,role,phone) VALUES (#{username}, #{password},#{role},#{phone})")
    Integer addUser(@Param("username") String username,@Param("password" )String password,@Param("role") String role,@Param("phone") String phone);
}
