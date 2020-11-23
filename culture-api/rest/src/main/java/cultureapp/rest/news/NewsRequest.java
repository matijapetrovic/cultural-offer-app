package cultureapp.rest.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsRequest {
    // TODO: Check if Long culturalOfferId is preferred (if not, make name unique)
    private String culturalOfferName;

    private String name;
    private String postedDate;
    private List<MultipartFile> images;
}
