package cultureapp.domain.subcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, SubcategoryId> {

    List<Subcategory> findAllByCategoryId(Long categoryId);
}
