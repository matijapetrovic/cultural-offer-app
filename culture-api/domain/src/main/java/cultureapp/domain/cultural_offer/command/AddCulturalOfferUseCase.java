package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.IdList;
import cultureapp.domain.core.validation.annotation.Latitude;
import cultureapp.domain.core.validation.annotation.Longitude;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public interface AddCulturalOfferUseCase {
    void addCulturalOffer(AddCulturalOfferCommand command) throws SubcategoryNotFoundException, ImageNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddCulturalOfferCommand extends SelfValidating<AddCulturalOfferCommand> {

        @NotBlank
        String name;
        @Longitude
        Double longitude;
        @Latitude
        Double latitude;
        @IdList
        List<Long> images;
        @NotBlank
        String description;
        @Positive
        Long categoryId;
        @Positive
        Long subcategoryId;

        public AddCulturalOfferCommand(
                String name,
                Double longitude,
                Double latitude,
                List<Long> images,
                String description,
                Long categoryId,
                Long subcategoryId) {
            this.name = name;
            this.longitude = longitude;
            this.latitude = latitude;
            this.images = images;
            this.description = description;
            this.categoryId= categoryId;
            this.subcategoryId = subcategoryId;
            this.validateSelf();
        }
    }
}
