package cultureapp.domain.authority;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name="authority")
public class Authority implements GrantedAuthority {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name")
    String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public static Authority withId(
            Long id,
            String name) {
        return new Authority(id, name);
    }

    public static Authority of(
            String name) {
        return withId(null, name);
    }

}
