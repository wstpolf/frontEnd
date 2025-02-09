package com.example.springbootproject.service.impl;

import com.example.springbootproject.mapper.MapperC;
import com.example.springbootproject.model.entity.Student;
import com.example.springbootproject.service.PaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaServiceImpl implements PaService {
    @Autowired
    private MapperC mapperC;

    @Override
    public Student getChild(String phone) {
        return mapperC.getChild(phone);
    }
}
