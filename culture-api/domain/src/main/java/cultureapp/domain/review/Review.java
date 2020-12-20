package cultureapp.domain.review;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.image.Image;
import cultureapp.domain.user.RegularUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="review_generator")
    @SequenceGenerator(name="review_generator", sequenceName = "review_id_seq", allocationSize = 1)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name="cultural_offer_id", insertable = false, updatable = false)
    private CulturalOffer culturalOffer;

    @Column(name="comment", length = 1000)
    private String comment;

    @Column(name="rating")
    private BigDecimal rating;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Image> images;

    @OneToOne(fetch = FetchType.EAGER)
    private Reply reply;

    @Column(name="archived", nullable = false)
    private boolean archived;

    public boolean addReply(Reply reply) {
        if (this.reply != null)
            return false;
        this.reply = reply;
        return true;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private RegularUser author;

    @Column(name="date")
    LocalDateTime date;

    public void archive() {
        this.archived = true;
    }

    public void unarchive() { this.archived = false; }

    public static Review withId(
            Long id,
            CulturalOffer culturalOffer,
            String comment,
            BigDecimal rating,
            boolean archived,
            List<Image> images,
            RegularUser user,
            LocalDateTime date
           ) {
        return new Review(
                id,
                culturalOffer,
                comment,
                rating,
                images,
                null,
                archived,
                user,
                date);
    }

    public static Review of(
            CulturalOffer culturalOffer,
            String comment,
            BigDecimal rating,
            boolean archived,
            List<Image> images,
            RegularUser user,
            LocalDateTime date
           ) {
        return withId(
                null,
                culturalOffer,
                comment,
                rating,
                archived,
                images,
                user,
                date);
    }
}
