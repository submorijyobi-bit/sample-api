package com.example.sampleapi.auth;

import com.example.sampleapi.dto.LoginRequest;
import com.example.sampleapi.dto.LoginResponse;
import com.example.sampleapi.exception.LoginArgumentNotValidException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
}
