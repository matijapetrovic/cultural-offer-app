package cultureapp.domain.image;

import java.util.List;

public interface ImageUploadService {
    List<String> uploadImages(List<byte[]> images);
}
