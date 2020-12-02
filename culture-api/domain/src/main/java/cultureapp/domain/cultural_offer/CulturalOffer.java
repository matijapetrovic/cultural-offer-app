package cultureapp.domain.cultural_offer;

import cultureapp.domain.regular_user.RegularUser;
import cultureapp.domain.subcategory.Subcategory;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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

    @ManyToMany(mappedBy = "culturalOffers")
    private Set<RegularUser> regularUsers;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "subcategory_id", referencedColumnName = "id"),
            @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    })
    private Subcategory subcategory;

    @Column(name="archived", nullable = false)
    private boolean archived;


    public static CulturalOffer withId(
            Long id,
            String name,
            String description,
            Location location,
            List<Image> images,
            Set<RegularUser> regularUsers,
            Subcategory subcategory) {
        return new CulturalOffer(
                id,
                name,
                description,
                location,
                images,
                regularUsers,
                subcategory,
                false);
    }

    public void archive() {
        this.archived = true;
    }

    public static CulturalOffer of(
            String name,
            String description,
            Location location,
            List<Image> images,
            Subcategory subcategory) {
        return withId(
                null,
                name,
                description,
                location,
                images,
                null,
                subcategory);
    }
}
