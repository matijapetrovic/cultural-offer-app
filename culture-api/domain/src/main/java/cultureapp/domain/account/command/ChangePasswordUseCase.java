package cultureapp.domain.account.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.Password;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.constraints.NotNull;

public interface ChangePasswordUseCase {
    boolean changePassword(ChangePasswordCommand command) throws AccountNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class ChangePasswordCommand extends SelfValidating<ChangePasswordCommand> {

        @NotNull
        String oldPassword;
        @Password
        String newPassword;

        public ChangePasswordCommand(
                String oldPassword,
                String newPassword) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
            this.validateSelf();
        }
    }
}
