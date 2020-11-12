package cultureapp.domain.core.example;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="example")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Example {
    @Id
    private Long id;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="number")
    private Integer number;

    public static Example withId(
            Long id,
            String email,
            Integer number) {
        return new Example(id, email, number);
    }

    public static Example of(
            String email,
            Integer number) {
        return withId(null, email, number);
    }
}
