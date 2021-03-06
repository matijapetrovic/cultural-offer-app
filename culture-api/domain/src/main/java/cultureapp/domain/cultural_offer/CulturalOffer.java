package cultureapp.domain.cultural_offer;

import cultureapp.domain.image.Image;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.subcategory.Subcategory;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name="cultural_offer")
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CulturalOffer {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", length = 1000)
    private String description;

    @Column(name="rating")
    private BigDecimal rating;

    @Column(name="review_count")
    private int reviewCount;

    @Embedded
    private Location location;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Image> images;

    @ManyToMany(mappedBy = "culturalOffers", fetch = FetchType.LAZY)
    private Set<RegularUser> subscribers;

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
            BigDecimal rating,
            int reviewCount,
            Location location,
            List<Image> images,
            Set<RegularUser> regularUsers,
            Subcategory subcategory) {
        return new CulturalOffer(
                id,
                name,
                description,
                rating,
                reviewCount,
                location,
                images,
                regularUsers,
                subcategory,
                false);
    }

    public static CulturalOffer of(
            String name,
            String description,
            BigDecimal rating,
            int reviewCount,
            Location location,
            List<Image> images,
            Set<RegularUser> regularUsers,
            Subcategory subcategory
    ) {
        return withId(null, name, description, rating, reviewCount, location, images, regularUsers, subcategory);
    }

    public void archive() {
        this.archived = true;
    }

    public static CulturalOffer of(
            String name,
            String description,
            BigDecimal rating,
            int reviewCount,
            Location location,
            List<Image> images,
            Subcategory subcategory) {
        return withId(
                null,
                name,
                description,
                rating,
                reviewCount,
                location,
                images,
                null,
                subcategory);
    }
}
