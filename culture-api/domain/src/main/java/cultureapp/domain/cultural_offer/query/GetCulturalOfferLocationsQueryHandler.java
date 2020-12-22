package cultureapp.domain.cultural_offer.query;

import cultureapp.domain.category.exception.CategoryNotFoundException;
import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.Latitude;
import cultureapp.domain.core.validation.annotation.Longitude;
import cultureapp.domain.core.validation.annotation.NullOrPositive;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.exception.CulturalOfferLocationsFilterException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.*;

import java.util.List;

public interface GetCulturalOfferLocationsQueryHandler {
    List<GetCulturalOfferLocationsDTO> handleGetCulturalOfferLocations(GetCulturalOfferLocationsQuery query) throws
            SubcategoryNotFoundException,
            CategoryNotFoundException,
            CulturalOfferLocationsFilterException;

    @Value
    @EqualsAndHashCode(callSuper = false)
    class GetCulturalOfferLocationsQuery extends SelfValidating<GetCulturalOfferLocationsQuery> {
        @Latitude
        Double latitudeFrom;

        @Latitude
        Double latitudeTo;

        @Longitude
        Double longitudeFrom;

        @Longitude
        Double longitudeTo;

        @NullOrPositive
        Long categoryId;

        @NullOrPositive
        Long subcategoryId;

        public GetCulturalOfferLocationsQuery(
                Double latitudeFrom,
                Double latitudeTo,
                Double longitudeFrom,
                Double longitudeTo,
                Long categoryId,
                Long subcategoryId
        ) {
            this.latitudeFrom = latitudeFrom;
            this.latitudeTo = latitudeTo;
            this.longitudeFrom = longitudeFrom;
            this.longitudeTo = longitudeTo;
            this.categoryId = categoryId;
            this.subcategoryId = subcategoryId;
            this.validateSelf();
        }
    }

    @Getter
    class GetCulturalOfferLocationsDTO {
        Long id;
        String name;
        Location location;

        public GetCulturalOfferLocationsDTO(Long id, String name, Location location) {
            this.id = id;
            this.name = name;
            this.location = location;
        }
    }
}
