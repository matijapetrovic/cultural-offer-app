package cultureapp.domain.authentication.exception;

public class AccountNotActivatedException extends Exception {
    public AccountNotActivatedException(String email) {
        super(String.format("Account with email %s not yet activated", email));
    }
}
