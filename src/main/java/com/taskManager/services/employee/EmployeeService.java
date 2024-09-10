package com.taskManager.services.employee;

import com.taskManager.dto.TaskDto;
import com.taskManager.dto.UserDto;

import java.util.List;

public interface EmployeeService {

    List<TaskDto> getTaskByUserId();

    TaskDto updateTask(Long id, String status);
}
