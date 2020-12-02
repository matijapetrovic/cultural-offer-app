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
    public List<Long> uploadImages(List<UploadImagesCommand> files) {
        List<String> paths = imageUploadService.uploadImages(
                files.stream().map(UploadImagesCommand::getFile).collect(Collectors.toList()));

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
