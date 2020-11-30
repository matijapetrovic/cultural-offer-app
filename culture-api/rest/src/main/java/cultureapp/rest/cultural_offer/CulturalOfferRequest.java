package cultureapp.rest.cultural_offer;

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
public class CulturalOfferRequest {
    String name;
    String description;
    Double longitude;
    Double latitude;
    Long subcategory;
}
