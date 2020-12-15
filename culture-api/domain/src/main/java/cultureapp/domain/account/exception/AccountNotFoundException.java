package cultureapp.domain.account.exception;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(Long accountId) {
        super(String.format("Account with id %d not found", accountId));
    }
}
