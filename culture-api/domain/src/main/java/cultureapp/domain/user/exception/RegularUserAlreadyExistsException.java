package cultureapp.domain.user.exception;

public class RegularUserAlreadyExistsException extends Exception {
    public RegularUserAlreadyExistsException(String email) {
        super(String.format("User with email %s already exists", email));
    }
}
