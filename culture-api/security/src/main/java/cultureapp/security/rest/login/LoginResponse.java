package cultureapp.security.rest.login;

import lombok.Value;

import java.util.List;

@Value
public class LoginResponse {
    String token;
    List<String> role;
    long expiresIn;
}