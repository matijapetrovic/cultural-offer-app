package cultureapp.domain.review;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    Slice<Review> findAllByCulturalOfferIdAndArchivedFalse(Long culturalOfferId, Pageable pageable);
    Optional<Review> findByIdAndCulturalOfferIdAndArchivedFalse(Long id, Long culturalOfferId);
}
