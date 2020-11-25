package cultureapp.domain.subcategory.exception;

public class SubcategoryNotFoundException extends Exception{
    public SubcategoryNotFoundException(Long subcategoryId) {
        super(String.format("Subcategory with id %d not found", subcategoryId));
    }
}
