package com.example.springbootproject.mapper;

import com.example.springbootproject.model.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MapperC {
    //获取孩子信息
    @Select("select * from students s where s.parent_phone = #{phone}")
    Student getChild(String phone);

}
