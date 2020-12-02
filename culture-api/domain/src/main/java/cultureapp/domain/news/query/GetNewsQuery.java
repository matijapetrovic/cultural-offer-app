package cultureapp.domain.news.query;

import cultureapp.domain.cultural_offer.Image;
import cultureapp.domain.news.News;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public interface GetNewsQuery {
    List<GetNewsDTO> getNews();

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Setter
    class GetNewsDTO {
        private Long id;
        private Long culturalOfferId;
        private String name;
        private LocalDateTime postedDate;
        private Long authorId;
        private String text;

        // Base64 String encoded images
        private List<String> images;

        public static GetNewsDTO of(News news) {
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

            return new GetNewsDTO(
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
