package cultureapp.domain.category.exception;

public class CategoryCannotBeDeletedException extends Exception {
    public CategoryCannotBeDeletedException(Long categoryId) {
        super(String.format("Category with id %d cannot be deleted", categoryId));
    }
}
