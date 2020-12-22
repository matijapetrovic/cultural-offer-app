package cultureapp.domain.image;

import cultureapp.domain.core.ImageUploader;
import cultureapp.domain.image.command.UploadImagesUseCase;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static cultureapp.common.ImageTestData.*;

public class ImageServiceUnitTest {
    private final ImageRepository imageRepository =
            Mockito.mock(ImageRepository.class);

    private final ImageUploader imageUploader =
            Mockito.mock(ImageUploader.class);

    private final ImageService imageService =
            new ImageService(imageRepository, imageUploader);

    @Test
    public void givenValidMimeTypesThenUploadImagesWillSucceed() {
        given(imageUploader.uploadImages(IMAGE_BYTES)).willReturn(IMAGE_PATHS);
        List<Image> images = IMAGE_IDS
                .stream()
                .map(id -> Image.withId(id, IMAGE_PATH))
                .collect(Collectors.toList());
        given(imageRepository.saveAll(notNull())).willReturn(images);

        UploadImagesUseCase.UploadImagesCommand command =
                new UploadImagesUseCase.UploadImagesCommand(IMAGE_BYTES, VALID_MIME_TYPES);

        List<Long> result = imageService.uploadImages(command);
        assertEquals(IMAGE_IDS.size(), result.size());

        then(imageUploader)
                .should()
                .uploadImages(notNull());

        then(imageRepository)
                .should()
                .saveAll(notNull());
    }

    @Test(expected = ConstraintViolationException.class)
    public void givenInvalidMimeTypesUploadImagesWillFail() {
        new UploadImagesUseCase.UploadImagesCommand(IMAGE_BYTES, INVALID_MIME_TYPES);
    }
}
