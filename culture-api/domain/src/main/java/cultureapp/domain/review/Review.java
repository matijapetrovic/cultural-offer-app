package cultureapp.domain.review;


import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.image.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="review")
@Entity
@IdClass(ReviewId.class)
public class Review {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name="cultural_offer_id", insertable = false, updatable = false)
    private CulturalOffer culturalOffer;

    @Column(name="comment")
    private String comment;

    @Column(name="rating")
    private BigDecimal rating;

    @ElementCollection
    private List<Image> images;

    @OneToOne(fetch = FetchType.EAGER)
    private Reply reply;

    public boolean addReply(Reply reply) {
        if (this.reply != null)
            return false;
        this.reply = reply;
        return true;
    }

    public static Review withId(
            Long id,
            CulturalOffer culturalOffer,
            String comment,
            BigDecimal rating,
            List<Image> images) {
        return new Review(
                id,
                culturalOffer,
                comment,
                rating,
                images,
                null);
    }

    public static Review of(
            CulturalOffer culturalOffer,
            String comment,
            BigDecimal rating,
            List<Image> images) {
        return withId(
                null,
                culturalOffer,
                comment,
                rating,
                images);
    }
}
