package cultureapp.domain.news;

import cultureapp.domain.administrator.Administrator;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="news")
@Entity
@IdClass(NewsId.class)
public class News {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name="cultural_offer_id", insertable = false, updatable = false)
    private CulturalOffer culturalOffer;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="posted_date")
    private LocalDateTime postedDate;

    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName = "id")
    private Administrator author;

    @Column(name = "text")
    private String text;

    @Column
    private Boolean archived;

    @ElementCollection
    private List<Image> images;

    public static News withId(
            Long id,
            CulturalOffer culturalOffer,
            String name,
            LocalDateTime postedDate,
            Administrator administrator,
            String text,
            Boolean archived,
            List<Image> images) {
        return new News(
                id,
                culturalOffer,
                name,
                postedDate,
                administrator,
                text,
                archived,
                images);
    }

    public static News of(
            CulturalOffer culturalOffer,
            String name,
            LocalDateTime postedDate,
            Administrator administrator,
            String text,
            Boolean archived,
            List<Image> images) {
        return withId(
                null,
                culturalOffer,
                name,
                postedDate,
                administrator,
                text,
                archived,
                images);
    }
}
