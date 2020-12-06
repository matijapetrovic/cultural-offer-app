package cultureapp.domain.user;

import cultureapp.domain.account.Account;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public void setPassword(String password) {
        Timestamp now = new Timestamp(new Date().getTime());
        this.account.setLastPasswordResetDate(now);
        this.account.setPassword(password);
    }

    public String getEmail() { return account.getEmail(); }

    public Timestamp getLastPasswordResetDate() {
        return account.getLastPasswordResetDate();
    }

}
