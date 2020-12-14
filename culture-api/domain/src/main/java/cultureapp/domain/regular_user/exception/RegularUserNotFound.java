package cultureapp.domain.regular_user.exception;

public class RegularUserNotFound extends Exception {
    public RegularUserNotFound(String email) {

        super(String.format("User with email %s already exists", email));
    }
}
