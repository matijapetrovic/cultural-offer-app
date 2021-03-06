package cultureapp.domain.reply;


import cultureapp.domain.review.Review;
import cultureapp.domain.review.ReviewId;
import cultureapp.domain.user.Administrator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name="reply")
@IdClass(ReviewId.class)
public class Reply {

    @Id
    @Column(name="id")
    private Long id;

    @Id
    @Column(name="cultural_offer_id")
    private Long culturalOffer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="id", referencedColumnName = "id")
    @JoinColumn(name="cultural_offer_id", referencedColumnName = "cultural_offer_id")
    @MapsId
    private Review review;

    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName = "id")
    private Administrator administrator;

    @Column(name="comment", length = 1000)
    private String comment;

    public static Reply of(
            Review review,
            String comment,
            Administrator administrator) {
        return new Reply(
                review.getId(),
                review.getCulturalOffer().getId(),
                review,
                administrator,
                comment);
    }
}
