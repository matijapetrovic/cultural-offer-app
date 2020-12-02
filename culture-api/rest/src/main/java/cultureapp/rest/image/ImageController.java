package cultureapp.rest.image;


import cultureapp.domain.image.command.UploadImagesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/api/images", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ImageController {
    private final UploadImagesUseCase uploadImagesUseCase;

    @PostMapping("")
    ResponseEntity<List<Long>> uploadImages(@RequestParam("images") List<MultipartFile> images) throws IOException {
        UploadImagesUseCase.UploadImagesCommand command = createCommand(images);
        List<Long> response = uploadImagesUseCase.uploadImages(command);
        return ResponseEntity.ok(response);
    }

    UploadImagesUseCase.UploadImagesCommand createCommand(List<MultipartFile> files) throws IOException {
        List<byte[]> images = new ArrayList<>();
        List<String> types = new ArrayList<>();

        for (MultipartFile file : files) {
            images.add(file.getBytes());
            types.add(file.getContentType());
        }

        return new UploadImagesUseCase.UploadImagesCommand(images, types);

    }
}
