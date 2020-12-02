package cultureapp.domain.category.exception;

public class CategoryAlreadyExists extends Exception {
    public CategoryAlreadyExists(String name) {
        super(String.format("Category with name %s already exists", name));
    }
}
