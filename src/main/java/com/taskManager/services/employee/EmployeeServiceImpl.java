package com.taskManager.services.employee;


import com.taskManager.dto.TaskDto;
import com.taskManager.dto.UserDto;
import com.taskManager.enums.TaskStatus;
import com.taskManager.models.Task;
import com.taskManager.models.User;
import com.taskManager.repository.TaskRepository;
import com.taskManager.repository.UserRepository;
import com.taskManager.utils.JwtUtil;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final TaskRepository taskRepository;

    private final JwtUtil jwtUtil;


    @Override
    public List<TaskDto> getTaskByUserId() {
        User user = jwtUtil.getLoggedInUser();
        if (user != null) {
            return taskRepository.findAllByUserId(user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDto)
                    .collect(Collectors.toList());
        }
        throw new EntityNotFoundException("User Not found");
    }

    @Override
    public TaskDto updateTask(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task exististingTask = optionalTask.get();
            exististingTask.setTaskStatus(mapStringToTaskStatus(status));
            return taskRepository.save(exististingTask).getTaskDto();
        }
        throw new EntityNotFoundException("Task Not found");
    }
    private TaskStatus mapStringToTaskStatus(String status){
        return switch(status.toUpperCase()){
            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}
