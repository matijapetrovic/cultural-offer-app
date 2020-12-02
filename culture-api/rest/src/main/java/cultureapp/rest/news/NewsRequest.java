package cultureapp.rest.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsRequest {
    private Long culturalOfferID;
    private String name;
    private Date postedDate;
    private Long authorID;

    // Base64 String encoded images
    private List<String> images;
}
