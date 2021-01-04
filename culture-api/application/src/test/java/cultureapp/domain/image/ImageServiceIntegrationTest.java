package cultureapp.domain.image;

import cultureapp.domain.image.command.UploadImagesUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

import static cultureapp.common.ImageTestData.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class ImageServiceIntegrationTest {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void givenValidImagesThenUploadImagesWillSucceed() throws IOException {
        long count = imageRepository.count();
        List<ClassPathResource> images = EXISTING_CLASSPATH_IMAGES
                .stream()
                .map(ClassPathResource::new)
                .collect(Collectors.toList());

        List<byte[]> bytes = new ArrayList<>();
        for (ClassPathResource image : images) {
            bytes.add(image.getInputStream().readAllBytes());
        }

        UploadImagesUseCase.UploadImagesCommand command =
                new UploadImagesUseCase.UploadImagesCommand(bytes, EXISTING_CLASSPATH_IMAGES_TYPES);
        List<Long> result = imageService.uploadImages(command);

        assertEquals(images.size() ,result.size());
        assertEquals(count + images.size(), imageRepository.count());
        imageRepository.deleteAll(imageRepository.findAllById(result));
    }
}
