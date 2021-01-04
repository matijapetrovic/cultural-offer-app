package cultureapp.domain.user.exception;

public class AdminNotFoundException extends Exception {
    public AdminNotFoundException(Long adminId) {
        super(String.format("Administrator with id %d not found", adminId));
    }
}
