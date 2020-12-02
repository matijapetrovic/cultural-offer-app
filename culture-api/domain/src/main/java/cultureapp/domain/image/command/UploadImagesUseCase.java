package cultureapp.domain.image.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.ImageTypes;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

public interface UploadImagesUseCase {
    List<Long> uploadImages(UploadImagesCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UploadImagesCommand extends SelfValidating<UploadImagesCommand> {

        List<byte[]> files;
        @ImageTypes
        List<String> types;
        public UploadImagesCommand(
                List<byte[]> files,
                List<String> types) {
            this.files = files;
            this.types = types;
            this.validateSelf();
        }
    }
}
