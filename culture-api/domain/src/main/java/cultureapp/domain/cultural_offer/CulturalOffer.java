package cultureapp.domain.cultural_offer;

import cultureapp.domain.regular_user.RegularUser;
import cultureapp.domain.subcategory.Subcategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "culturalOffers")
    private Set<RegularUser> regularUsers;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "subcategory_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    })
    private Subcategory subcategory;

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
                subcategory);
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
