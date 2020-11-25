package cultureapp.domain.account;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public static Account withId(
            Long id,
            String email,
            String password
    ) {
        return new Account(
                id,
                email,
                password
        );
    }

    public static Account of(
            String email,
            String password
    ) {
        return withId(null, email, password);
    }


}
