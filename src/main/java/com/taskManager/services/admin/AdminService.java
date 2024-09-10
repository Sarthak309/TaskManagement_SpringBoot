package com.taskManager.services.admin;


import com.taskManager.dto.CommentDto;
import com.taskManager.dto.TaskDto;
import com.taskManager.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> getUsers();

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTask();

    void deleteTask(Long id);

    TaskDto getTaskById(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    List<TaskDto> searchTaskByTitle(String title);

    CommentDto createComment(Long taskId, String content);

    List<CommentDto> getCommentsByTaskId(Long taskId);
}
