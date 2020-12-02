package cultureapp.authentication.exception;

public class AccountNotActivatedException extends Exception{
    public AccountNotActivatedException() {
        super("Account not activated!");
    }
}
