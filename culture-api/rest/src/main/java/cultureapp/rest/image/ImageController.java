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
        List<UploadImagesUseCase.UploadImagesCommand> command = createCommands(images);
        List<Long> response = uploadImagesUseCase.uploadImages(command);
        return ResponseEntity.ok(response);
    }

    List<UploadImagesUseCase.UploadImagesCommand> createCommands(List<MultipartFile> files) throws IOException {
        List<UploadImagesUseCase.UploadImagesCommand> commands = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadImagesUseCase.UploadImagesCommand command = new UploadImagesUseCase.UploadImagesCommand(
                    file.getContentType(),
                    file.getBytes());
            commands.add(command);
        }

        return commands;
    }
}
