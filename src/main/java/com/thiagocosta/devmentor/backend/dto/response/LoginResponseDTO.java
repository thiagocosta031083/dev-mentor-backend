package com.thiagocosta.devmentor.backend.dto.response;

public class LoginResponseDTO {
    private final String token;
    private final String email;

    public LoginResponseDTO(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}
