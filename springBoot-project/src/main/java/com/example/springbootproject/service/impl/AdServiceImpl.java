package com.example.springbootproject.service.impl;

import com.example.springbootproject.mapper.MapperD;
import com.example.springbootproject.model.entity.User;
import com.example.springbootproject.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    private MapperD mapperD;

    @Override
    public List<User> getUsers() {
        return mapperD.getAllUser();
    }

    @Override
    public void delUser(Integer userId) {
        mapperD.delUser(userId);
    }
}
