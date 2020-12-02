package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public interface UpdateCulturalOfferUseCase {
    void updateCulturalOffer(UpdateCulturalOfferCommand command) throws CulturalOfferNotFoundException, SubcategoryNotFoundException;

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

        Long categoryId;

        Long subcategoryId;

        public UpdateCulturalOfferCommand(Long id, String name, String description,
                                          Double longitude, Double latitude,
                                          Long categoryId, Long subcategoryId)
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.longitude = longitude;
            this.latitude = latitude;
            this.categoryId = categoryId;
            this.subcategoryId = subcategoryId;
        }
    }
}
