package cultureapp.security;

import cultureapp.domain.authentication.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BCryptEncoder implements PasswordEncoder {
    private final org.springframework.security.crypto.password.PasswordEncoder encoder;

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }
}
