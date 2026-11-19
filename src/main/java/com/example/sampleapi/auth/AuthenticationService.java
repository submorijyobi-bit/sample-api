package com.example.sampleapi.auth;

import com.example.sampleapi.dto.LoginRequest;
import com.example.sampleapi.dto.LoginResponse;
import com.example.sampleapi.dto.RegisterRequest;
import com.example.sampleapi.exception.LoginArgumentNotValidException;
import com.example.sampleapi.exception.UserRegistrationException;
import com.example.sampleapi.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse authentication(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()){
            throw new LoginArgumentNotValidException("Username or password is incorrect");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()){
            throw new LoginArgumentNotValidException("Username or password is incorrect");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
//       JWT トークンを生成
        String token = jwtService.generateToken(loginRequest.getUsername());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return loginResponse;
    }

    public LoginResponse register(RegisterRequest registerRequest) {
//        バリデーション
        if (registerRequest.getUsername() == null || registerRequest.getUsername().isEmpty()){
            throw new LoginArgumentNotValidException("username is empty");
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()){
            throw new LoginArgumentNotValidException("password is empty");
        }
        if (userRepository.selectUserByUsername(registerRequest.getUsername()) != null){
            throw new UserRegistrationException("username already exists");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        userRepository.insertUser(registerRequest.getUsername(), encodedPassword);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtService.generateToken(registerRequest.getUsername()));
        return loginResponse;
    }
}
