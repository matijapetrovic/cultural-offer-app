package cultureapp.domain.subcategory.exception;

public class SubcategoryNotFoundException extends Exception {
    public SubcategoryNotFoundException(Long subcategoryId, Long categoryId) {
        super(String.format("Subcategory with id %d not found in category with id %d", subcategoryId, categoryId));
    }
}
