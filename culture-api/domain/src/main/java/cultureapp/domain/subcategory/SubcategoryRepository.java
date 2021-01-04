package cultureapp.domain.subcategory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, SubcategoryId> {
    Slice<Subcategory> findAllByCategoryIdAndArchivedFalse(Long categoryId, Pageable pageable);
    List<Subcategory> findAllByCategoryIdAndArchivedFalse(Long categoryId);
    Optional<Subcategory> findByIdAndCategoryIdAndArchivedFalse(Long id, Long categoryId);
}
