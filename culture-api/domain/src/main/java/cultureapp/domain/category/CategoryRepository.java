package cultureapp.domain.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Slice<Category> findAllByArchivedFalse(Pageable pageable);
    Optional<Category> findByIdAndArchivedFalse(Long id);
}
