package cultureapp.domain.news.query;

import cultureapp.domain.cultural_offer.Image;
import cultureapp.domain.news.News;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public interface GetNewsByIdQuery {
    GetNewsByIdDTO getNewsById(@Positive Long id, @Positive Long culturalOfferId);

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    class GetNewsByIdDTO {
        private Long id;
        private Long culturalOfferId;
        private String name;
        private LocalDateTime postedDate;
        private Long authorId;
        private String text;

        // Base64 String encoded images
        private List<String> images;

        public static GetNewsByIdDTO of(News news) {
            // Convert images to base64 string
            List<String> base64Files = new ArrayList<>();
            try {
                for (Image image : news.getImages()) {
//                    File imageFile = new File(image.getPath());
//                    FileInputStream reader = new FileInputStream(imageFile);
//                    byte[] fileBytes = new byte[(int) imageFile.length()];
//                    reader.read(fileBytes);

                    byte[] fileBytes = Files.readAllBytes(Paths.get(image.getPath()));
                    String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
                    base64Files.add(encodedFile);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new GetNewsByIdDTO(
                    news.getId(),
                    news.getCulturalOffer().getId(),
                    news.getName(),
                    news.getPostedDate(),
                    news.getAuthor().getId(),
                    news.getText(),
                    base64Files
            );
        }
    }
}
