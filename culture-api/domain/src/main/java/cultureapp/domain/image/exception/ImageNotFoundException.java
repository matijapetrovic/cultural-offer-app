package cultureapp.domain.image.exception;

public class ImageNotFoundException extends Exception {
    public ImageNotFoundException(Long imageId) {
        super(String.format("Image with id %d not found", imageId));
    }
}
