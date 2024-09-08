package com.taskManager.services.auth;

import com.taskManager.dto.SignupRequest;
import com.taskManager.dto.UserDto;

public interface AuthService {

    UserDto signupUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}
