package cultureapp.domain.cultural_offer;

import cultureapp.domain.cultural_offer.query.GetCulturalOfferLocationsQueryHandler;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Long> {
    Optional<CulturalOffer> findByIdAndArchivedFalse(Long id);
    Slice<CulturalOffer> findAllByArchivedFalse(Pageable pageable);


    @Query(value="select new cultureapp.domain.cultural_offer.query.GetCulturalOfferLocationsQueryHandler$GetCulturalOfferLocationsDTO(co.id, co.name, co.location) " +
            "from CulturalOffer co " +
            "where (:categoryId is null or co.subcategory.category.id = :categoryId) " +
            "and (:subcategoryId is null or co.subcategory.id = :subcategoryId) " +
            "and (  co.location.latitude >= :latitudeFrom " +
            "   and co.location.latitude <= :latitudeTo " +
            "   and co.location.longitude >= :longitudeFrom " +
            "   and co.location.longitude <= :longitudeTo)")
    List<GetCulturalOfferLocationsQueryHandler.GetCulturalOfferLocationsDTO> findByLocationAndCategoryIdAndSubcategoryId(
            @Param(value="latitudeFrom") Double latitudeFrom,
            @Param(value="latitudeTo") Double latitudeTo,
            @Param(value="longitudeFrom") Double longitudeFrom,
            @Param(value="longitudeTo") Double longitudeTo,
            @Param(value="categoryId") Long categoryId,
            @Param(value="subcategoryId") Long subcategoryId
    );
}
