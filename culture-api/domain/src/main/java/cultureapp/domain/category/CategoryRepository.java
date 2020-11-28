package cultureapp.domain.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    List<Category> findAllByArchivedFalse(Pageable pageable);
    Optional<Category> findByIdAndArchivedFalse(Long id);
}
