package cultureapp.domain.user.exception;

public class AdminNotFoundException extends Exception {
    public AdminNotFoundException(Long adminId) {
        super(String.format("Administrator with id %d not found", adminId));
    }

    public AdminNotFoundException(String email) {
        super(String.format("Administrator with email %s not found", email));
    }
}
