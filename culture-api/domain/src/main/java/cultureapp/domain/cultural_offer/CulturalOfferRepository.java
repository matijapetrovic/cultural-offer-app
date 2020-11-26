package cultureapp.domain.cultural_offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Long> {
}
