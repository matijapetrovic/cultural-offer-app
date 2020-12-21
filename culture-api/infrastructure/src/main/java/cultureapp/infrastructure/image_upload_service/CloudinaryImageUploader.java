package cultureapp.infrastructure.image_upload_service;

import com.cloudinary.utils.ObjectUtils;
import cultureapp.domain.core.ImageUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Component
public class CloudinaryImageUploader implements ImageUploader {
    @Value("${CLOUDINARY_CLOUD_NAME}")
    private String cloudName;

    @Value("${CLOUDINARY_API_KEY}")
    private String apiKey;

    @Value("${CLOUDINARY_API_SECRET}")
    private String apiSecret;

    private Cloudinary cloudinary;

    @Override
    public List<String> uploadImages(List<byte[]> images) {
        if (cloudinary == null)
            initCloudinary();
        return images
                .stream()
                .map(image ->
                        uncheckCall(() -> this.uploadImage(image)))
                .collect(Collectors.toList());
    }

    private void initCloudinary() {
        cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret
                )
        );
    }


    private String uploadImage(byte[] image) throws IOException {
        Map result = cloudinary
                .uploader()
                .upload(image, ObjectUtils.emptyMap());
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
