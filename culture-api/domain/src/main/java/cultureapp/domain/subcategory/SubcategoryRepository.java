package cultureapp.domain.subcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, SubcategoryId> {

    List<Subcategory> findAllByCategoryIdAndArchivedFalse(Long categoryId);
    Optional<Subcategory> findByCategoryIdAndIdAndArchivedFalse(Long categoryId, Long id);
}
