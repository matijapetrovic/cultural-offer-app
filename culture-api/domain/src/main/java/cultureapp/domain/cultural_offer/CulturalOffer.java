package cultureapp.domain.cultural_offer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="cultural_offer")
@Entity
public class CulturalOffer {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @Embedded
    private Location location;

    @ElementCollection
    private List<Image> images;

    public static CulturalOffer withId(
            Long id,
            String name,
            String description,
            Location location,
            List<Image> images) {
        return new CulturalOffer(
                id,
                name,
                description,
                location,
                images);
    }

    public static CulturalOffer of(
            String name,
            String description,
            Location location,
            List<Image> images) {
        return withId(
                null,
                name,
                description,
                location,
                images);
    }
}
