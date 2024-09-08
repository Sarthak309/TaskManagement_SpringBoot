package com.taskManager.services.auth;


import com.taskManager.dto.SignupRequest;
import com.taskManager.dto.UserDto;
import com.taskManager.enums.UserRole;
import com.taskManager.models.User;
import com.taskManager.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{


    private final UserRepository userRepository;


    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> optionalUSer = userRepository.findByUserRole(UserRole.ADMIN);
        if(optionalUSer.isEmpty()){
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created successfully");
        }else{
            System.out.println("Admin Account Already Exists");
        }
    }

    @Override
    public UserDto signupUser(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.EMPLOYEE);
        User createdUser = userRepository.save(user);
        return createdUser.getUSerDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
