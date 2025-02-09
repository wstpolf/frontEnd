package com.example.springbootproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String phone;
    private String role;
}
