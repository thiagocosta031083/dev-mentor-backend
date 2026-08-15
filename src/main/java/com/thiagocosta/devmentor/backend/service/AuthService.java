package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.dto.request.LoginRequestDTO;
import com.thiagocosta.devmentor.backend.dto.response.LoginResponseDTO;
import com.thiagocosta.devmentor.backend.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager; private final JwtService jwt;
    public AuthService(AuthenticationManager authenticationManager,JwtService jwt){this.authenticationManager=authenticationManager;this.jwt=jwt;}
    public LoginResponseDTO login(LoginRequestDTO request){String email=request.getEmail().trim().toLowerCase();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,request.getSenha()));
        return new LoginResponseDTO(jwt.gerar(email),email);}
}
