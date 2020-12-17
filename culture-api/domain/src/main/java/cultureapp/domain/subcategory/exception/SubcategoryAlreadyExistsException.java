package cultureapp.domain.subcategory.exception;

public class SubcategoryAlreadyExistsException extends Exception {
    public SubcategoryAlreadyExistsException(String name) {
        super(String.format("Subcategory with name %s already exists", name));
    }
}
