package com.example.springbootproject.controller;

import com.example.springbootproject.model.entity.User;
import com.example.springbootproject.service.AdService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController {
    @Autowired
    private AdService adService;

    //获取用户信息
    @GetMapping("/AllUser")
    public List<User> getAllUser(){
        return adService.getUsers();
    }

    //删除账户
    @DeleteMapping("/delUser/{userId}")
    public ResponseEntity<String> delUser(@PathVariable Integer userId){
        adService.delUser(userId);
        return ResponseEntity.ok("success");

    }

}
