package com.example.springbootproject.service;

import com.example.springbootproject.model.entity.User;

import java.util.List;

public interface AdService {
    List<User> getUsers();

    void delUser(Integer userId);
}
