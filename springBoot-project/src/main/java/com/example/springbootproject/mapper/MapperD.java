package com.example.springbootproject.mapper;

import com.example.springbootproject.model.dto.UserResponse;
import com.example.springbootproject.model.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MapperD {
    @Select("select * from users where 1=1")
    List<User> getAllUser();
    //删除用户
    @Delete("delete from users where id = #{userId}")
    void delUser(Integer userId);
}
