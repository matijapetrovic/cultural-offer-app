package cultureapp.rest.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewRequest {
    // TODO: check if Long culturalOfferId is preferred (if not, make name unique)
    private String culturalOffer;
    private String comment;
    private BigDecimal rating;
    private List<MultipartFile> images;
    private String reply;

    // TODO: Reply and administrator classes do not have references to one another
    // TODO: but it is specified in class diagram
    private String adminEmail;
}
