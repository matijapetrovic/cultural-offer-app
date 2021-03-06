package cultureapp.domain.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Slice<Category> findAllByArchivedFalse(Pageable pageable);

    Optional<Category> findByIdAndArchivedFalse(Long id);

    @Query("select case when count (sc)>0 then true else false end " +
            "from Subcategory sc where sc.category.id = :id and sc.archived=false ")
    boolean existsWithSubcategory(@Param(value="id") Long id);

    List<Category> findAllByArchivedFalse();
}
