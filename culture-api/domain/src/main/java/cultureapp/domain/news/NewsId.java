package cultureapp.domain.news;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class NewsId implements Serializable {
    private Long culturalOffer;
    private Long id;

    public static NewsId of(Long id, Long culturalOfferId) {
        return new NewsId(culturalOfferId, id);
    }
}
