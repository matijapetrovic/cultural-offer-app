package cultureapp.domain.review;


import cultureapp.domain.administrator.Administrator;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="reply_generator")
    @SequenceGenerator(name="reply_generator", sequenceName = "reply_id_seq", allocationSize = 1)
    private Long id;

    @Id
    @Column(name="cultural_offer_id")
    private Long culturalOffer;

    @OneToOne(fetch = FetchType.LAZY)
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
