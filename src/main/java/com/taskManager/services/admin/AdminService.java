package com.taskManager.services.admin;


import com.taskManager.dto.TaskDto;
import com.taskManager.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> getUsers();

    TaskDto createTask(TaskDto taskDto);
}
