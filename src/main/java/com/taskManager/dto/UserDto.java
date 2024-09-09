package com.taskManager.dto;

import com.taskManager.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;

}
