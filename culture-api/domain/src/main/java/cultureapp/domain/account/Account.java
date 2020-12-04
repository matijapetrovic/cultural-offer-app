package cultureapp.domain.account;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
