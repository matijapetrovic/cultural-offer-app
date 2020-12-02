package cultureapp.rest.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsRequest {
    private Long culturalOfferID;
    private String name;
    private LocalDateTime postedDate;
    private Long authorID;
    private String text;

}
