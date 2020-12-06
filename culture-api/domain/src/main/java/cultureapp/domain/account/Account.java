package cultureapp.domain.account;

import cultureapp.domain.authority.Authority;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Column(name="password_changed")
    private boolean passwordChanged;

    @Column(name="activated")
    private boolean activated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public static Account withId(
            Long id,
            String email,
            String password,
            Timestamp lastPasswordResetDate,
            boolean passwordChanged,
            boolean activated,
            List<Authority> authorities
    ) {
        return new Account(
                id,
                email,
                password,
                lastPasswordResetDate,
                passwordChanged,
                activated,
                authorities
        );
    }

    public static Account of(
            String email,
            String password,
            Timestamp lastPasswordResetDate,
            boolean passwordChanged,
            boolean activated,
            List<Authority> authorities
    ) {
        return withId(null, email, password,
                lastPasswordResetDate, passwordChanged,
                activated, authorities);
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public boolean changePassword(String newPassword) {
        if (password.equalsIgnoreCase(newPassword))
            return false;

        password = newPassword;
        passwordChanged = true;
        return true;
    }

    public void activate() {
        this.activated = true;
    }
}
