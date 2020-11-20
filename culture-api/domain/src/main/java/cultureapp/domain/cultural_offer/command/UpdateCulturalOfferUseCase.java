package cultureapp.domain.cultural_offer.command;

import cultureapp.domain.core.validation.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public interface UpdateCulturalOfferUseCase {
    void updateCulturalOffer(UpdateCulturalOfferCommand command);

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

        public UpdateCulturalOfferCommand(Long id, String name, String description,
                                          Double longitude, Double latitude)
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }
}
