package com.taskManager.services.admin;

import com.taskManager.dto.TaskDto;
import com.taskManager.dto.UserDto;
import com.taskManager.enums.TaskStatus;
import com.taskManager.enums.UserRole;
import com.taskManager.models.Task;
import com.taskManager.models.User;
import com.taskManager.repository.TaskRepository;
import com.taskManager.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployee());

        if(optionalUser.isPresent()){
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setDueDate(taskDto.getDueDate());
            task.setPriority(taskDto.getPriority());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDto();
        }
        return null;
    }
}
