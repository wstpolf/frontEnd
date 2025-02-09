package com.example.springbootproject.controller;


import com.example.springbootproject.model.entity.Student;
import com.example.springbootproject.service.PaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pa")
public class PaController {
    @Autowired
    private PaService paService;
    //根据phone获得孩子信息
    @GetMapping("/getChild/{phone}")
    public ResponseEntity<Student> getChild(@PathVariable String phone){
        return ResponseEntity.ok(paService.getChild(phone));
    }
}
