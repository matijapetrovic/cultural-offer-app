package cultureapp.domain.subcategory.exception;

public class SubcategoryAlreadyExists extends Exception {
    public SubcategoryAlreadyExists(String name) {
        super(String.format("Subcategory with name %s already exists", name));
    }
}
