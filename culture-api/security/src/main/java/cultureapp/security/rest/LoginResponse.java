package cultureapp.security.rest;

import lombok.Value;

import java.util.List;

@Value
public class LoginResponse {
    String token;
    List<String> role;
    long expiresIn;
}