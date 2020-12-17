package cultureapp.domain.core;

import java.util.List;

public interface ImageUploader {
    List<String> uploadImages(List<byte[]> images);
}
