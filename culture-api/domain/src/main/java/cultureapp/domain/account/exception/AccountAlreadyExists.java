package cultureapp.domain.account.exception;

public class AccountAlreadyExists extends Exception {
    public AccountAlreadyExists(String email) {
        super(String.format("User with email %s already exists", email));
    }
}
