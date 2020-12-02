package cultureapp.domain.image.command;

import cultureapp.domain.core.validation.SelfValidating;
import cultureapp.domain.core.validation.annotation.ImageType;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.persistence.Column;
import java.util.List;

public interface UploadImagesUseCase {
    List<Long> uploadImages(List<UploadImagesCommand> command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UploadImagesCommand extends SelfValidating<UploadImagesCommand> {

        @ImageType
        String type;
        byte[] file;

        public UploadImagesCommand(
                String type,
                byte[] file) {
            this.type = type;
            this.file = file;
            this.validateSelf();
        }
    }
}
