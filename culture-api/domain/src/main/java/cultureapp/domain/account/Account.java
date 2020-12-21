package cultureapp.domain.account;

import cultureapp.domain.account.exception.AccountAlreadyActivatedException;
import cultureapp.domain.authority.Authority;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="activated")
    private boolean activated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_authority",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;

    public boolean hasRole(String role) {
        return authorities
                .stream()
                .anyMatch(authority -> role.equals(authority.getName()));
    }

    public static Account withId(
            Long id,
            String email,
            String password,
            boolean activated,
            Set<Authority> authorities
    ) {
        return new Account(
                id,
                email,
                password,
                activated,
                authorities
        );
    }

    public static Account of(
            String email,
            String password,
            boolean activated,
            Set<Authority> authorities
    ) {
        return withId(null, email, password, activated, authorities);
    }

    public boolean changePassword(String newPassword) {
        if (password.equalsIgnoreCase(newPassword))
            return false;

        password = newPassword;
        return true;
    }

    public void activate() throws AccountAlreadyActivatedException {
        if (activated)
            throw new AccountAlreadyActivatedException(id);
        activated = true;
    }
}
