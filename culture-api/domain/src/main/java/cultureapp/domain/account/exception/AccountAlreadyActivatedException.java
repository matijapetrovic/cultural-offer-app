package cultureapp.domain.account.exception;

public class AccountAlreadyActivatedException extends Exception {
    public AccountAlreadyActivatedException(Long accountId) {
        super(String.format("Account with id %d already activated", accountId));
    }
}