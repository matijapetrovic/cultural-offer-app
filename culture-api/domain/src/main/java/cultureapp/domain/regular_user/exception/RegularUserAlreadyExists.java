package cultureapp.domain.regular_user.exception;

public class RegularUserAlreadyExists extends Exception {
    public RegularUserAlreadyExists(String email) {
        super(String.format("User with email %s already exists", email));
    }
}
