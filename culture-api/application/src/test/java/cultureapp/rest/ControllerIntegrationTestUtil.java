package cultureapp.rest;

import cultureapp.domain.authentication.command.LoginUseCase;
import cultureapp.rest.authentication.LoginRequest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class ControllerIntegrationTestUtil {

    public static String login(TestRestTemplate restTemplate, String username, String password) {
        LoginRequest request = new LoginRequest(username, password);
        ResponseEntity<LoginUseCase.LoginDTO> response =
                restTemplate.postForEntity("/api/auth/login", request, LoginUseCase.LoginDTO.class);
        return Objects.requireNonNull(response.getBody()).getToken();
    }

    public static HttpHeaders getHeaders(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", String.format("Bearer %s", token));
        return httpHeaders;
    }
}
