package com.taskManager.dto;

import com.taskManager.enums.TaskStatus;

import java.util.Date;

import lombok.Data;

@Data
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private Date dueDate;

    private String priority;

    private TaskStatus taskStatus;

    private Long employee;

    private String employeeName;

}
