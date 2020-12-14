package cultureapp.domain.authentication;

public interface PasswordEncoder {
    String encode(String password);
}
