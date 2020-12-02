package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface AddCulturalOfferUseCase {
    void addCulturalOffer(AddCulturalOfferCommand command) throws SubcategoryNotFoundException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddCulturalOfferCommand extends SelfValidating<AddCulturalOfferCommand> {

        @NotBlank
        String name;
        
        Double longitude;

        Double latitude;

        List<byte[]> images;

        String description;

        Long categoryId;

        Long subcategoryId;

        public AddCulturalOfferCommand(
                String name,
                Double longitude,
                Double latitude,
                List<byte[]> images,
                String description,
                Long categoryId,
                Long subcategoryId
        ) {
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
