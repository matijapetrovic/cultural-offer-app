package cultureapp.domain.account.exception;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String email) {
        super(String.format("User with email %s already exists", email));
    }
}
