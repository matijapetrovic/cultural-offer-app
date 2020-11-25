package cultureapp.domain.review;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class ReviewId implements Serializable {
    private Long culturalOffer;
    private Long id;
}
