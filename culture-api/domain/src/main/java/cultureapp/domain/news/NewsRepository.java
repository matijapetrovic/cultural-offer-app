package cultureapp.domain.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, NewsId> {
    Optional<News> findByIdAndCulturalOfferIdAndArchivedFalse(Long id, Long culturalOfferId);
}
