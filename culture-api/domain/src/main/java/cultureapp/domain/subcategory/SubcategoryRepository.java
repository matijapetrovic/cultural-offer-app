package cultureapp.domain.subcategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, SubcategoryId> {

    List<Subcategory> findAllByCategoryId(Long categoryId);
}
