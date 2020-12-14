package cultureapp.domain.image;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name="image")
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name="url", nullable = false)
    String url;

    public static Image withId(
            Long id,
            String path) {
        return new Image(id, path);
    }

    public static Image of(
            String path) {
        return withId(null, path);
    }
}
