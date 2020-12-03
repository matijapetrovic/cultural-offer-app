package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.IdList;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

public interface UpdateCulturalOfferUseCase {
    void updateCulturalOffer(UpdateCulturalOfferCommand command) throws CulturalOfferNotFoundException, SubcategoryNotFoundException, ImageNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = true)
    class UpdateCulturalOfferCommand extends SelfValidating<UpdateCulturalOfferCommand> {
        @Positive
        Long id;

        @NotBlank
        String name;

        @NotBlank
        String description;

        @NotBlank
        Double longitude;

        @NotBlank
        Double latitude;

        @Positive
        Long categoryId;

        @Positive
        Long subcategoryId;

        @IdList
        List<Long> images;

        public UpdateCulturalOfferCommand(Long id, String name, String description,
                                          Double longitude, Double latitude,
                                          Long categoryId, Long subcategoryId, List<Long> images)
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.longitude = longitude;
            this.latitude = latitude;
            this.categoryId = categoryId;
            this.subcategoryId = subcategoryId;
            this.images = images;
        }
    }
}
