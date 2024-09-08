package com.taskManager.controllers.auth;

import com.taskManager.dto.AuthenticationRequest;
import com.taskManager.dto.AuthenticationResponse;
import com.taskManager.dto.SignupRequest;
import com.taskManager.dto.UserDto;
import com.taskManager.models.User;
import com.taskManager.repository.UserRepository;
import com.taskManager.services.auth.AuthService;
import com.taskManager.services.jwt.UserService;
import com.taskManager.utils.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.swing.text.html.Option;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists");
        }
        UserDto createdUserDto = authService.signupUser(signupRequest);
        if(createdUserDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);

    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            System.out.println("Trying to authenticate");
            System.out.println("with email: "+ authenticationRequest.getEmail());
            System.out.println("with password: "+ authenticationRequest.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
            System.out.println("Authenticating");
        }catch (BadCredentialsException e){
            throw  new BadCredentialsException("Incorrect Username or Password");
        }catch (Exception e){
            System.out.println("Exception Caught: "+ e);
        }
        System.out.println("Authenticated");
        final UserDetails userDetails = userService.userDetailService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser =  userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        System.out.println(authenticationResponse);
        return authenticationResponse;
    }

}
