package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface AddCulturalOfferUseCase {
    void addCulturalOffer(AddCulturalOfferCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class AddCulturalOfferCommand extends SelfValidating<AddCulturalOfferCommand> {
        @NotBlank
        String name;

        @NotBlank
        Double longitude;

        @NotBlank
        Double latitude;

        List<byte[]> images;

        String description;

        public AddCulturalOfferCommand(
                String name,
                Double longitude,
                Double latitude,
                List<byte[]> images,
                String description
        ) {
            this.name = name;
            this.longitude = longitude;
            this.latitude = latitude;
            this.images = images;
            this.description = description;
            this.validateSelf();
        }
    }
}
