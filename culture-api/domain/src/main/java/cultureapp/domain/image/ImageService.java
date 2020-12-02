package cultureapp.domain.image;

import cultureapp.domain.image.command.UploadImagesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService implements UploadImagesUseCase {
    private final ImageRepository imageRepository;
    private final ImageUploadService imageUploadService;

    @Override
    public List<Long> uploadImages(UploadImagesCommand command) {
        List<String> paths = imageUploadService.uploadImages(command.getFiles());

        List<Image> images = paths
                .stream()
                .map(Image::of)
                .collect(Collectors.toList());

        return imageRepository.saveAll(images)
                .stream()
                .map(Image::getId)
                .collect(Collectors.toList());
    }
}
