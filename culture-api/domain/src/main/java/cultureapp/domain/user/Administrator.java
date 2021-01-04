package cultureapp.domain.user;

import cultureapp.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "administrator")
public class Administrator extends User {

    private Administrator(
            Long id,
            String firstName,
            String secondName,
            Account account
    ) {
        super(id, firstName, secondName, account);
    }

    public static Administrator withId(
        Long id,
        String firstName,
        String lastName,
        Account account
    ) {
        return new Administrator(
                id,
                firstName,
                lastName,
                account
        );
    }

    public static Administrator of(
            String firstName,
            String lastName,
            Account account
    ) {
        return withId(null, firstName, lastName, account);
    }
}
