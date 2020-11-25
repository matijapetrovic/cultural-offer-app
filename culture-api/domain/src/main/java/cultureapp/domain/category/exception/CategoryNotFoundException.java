package cultureapp.domain.category.exception;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(Long categoryId) {
        super(String.format("Category with id %d not found", categoryId));
    }
}
