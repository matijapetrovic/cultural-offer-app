package cultureapp.domain.subcategory;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class SubcategoryId implements Serializable {
    private Long category;
    private Long id;
}