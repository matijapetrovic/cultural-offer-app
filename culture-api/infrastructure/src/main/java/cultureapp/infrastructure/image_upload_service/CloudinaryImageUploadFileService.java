package cultureapp.infrastructure.image_upload_service;

import com.cloudinary.utils.ObjectUtils;
import cultureapp.domain.image.ImageUploadService;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Component
public class CloudinaryImageUploadFileService implements ImageUploadService {
    private final Cloudinary cloudinary = new Cloudinary(
            ObjectUtils.asMap(
                    "cloud_name", "culture-app",
                    "api_key", "369175885537818",
                    "api_secret", "DVzanMBkXgLSPO6F1H0ltb1MYRs"
            )
    );
    @Override
    public List<String> uploadImages(List<byte[]> images) {
        return images
                .stream()
                .map(image ->
                        uncheckCall(() -> this.uploadImage(image)))
                .collect(Collectors.toList());
    }

    private String uploadImage(byte[] image) throws IOException {
        Map result = cloudinary.uploader().upload(image, ObjectUtils.emptyMap());
        return (String) result.get("url");
    }

    private static <T> T uncheckCall(Callable<T> callable) {
        try {
            return callable.call();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
