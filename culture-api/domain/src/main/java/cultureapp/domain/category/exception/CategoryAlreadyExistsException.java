package cultureapp.domain.category.exception;

public class CategoryAlreadyExistsException extends Exception {
    public CategoryAlreadyExistsException(String name) {
        super(String.format("Category with name %s already exists", name));
    }
}
