package cultureapp.domain.user.exception;

public class RegularUserNotFoundException extends Exception {
    public RegularUserNotFoundException(String email) {

        super(String.format("User with email %s already exists", email));
    }
}
