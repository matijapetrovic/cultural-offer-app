package cultureapp.domain.cultural_offer;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Image {
    @Column(name="path", nullable = false, unique = true)
    String path;
}
