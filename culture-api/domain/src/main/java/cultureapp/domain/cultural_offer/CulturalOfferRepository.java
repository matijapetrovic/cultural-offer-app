package cultureapp.domain.cultural_offer;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Long> {
    Optional<CulturalOffer> findByIdAndArchivedFalse(Long id);
    Slice<CulturalOffer> findAllByArchivedFalse(Pageable pageable);
}
