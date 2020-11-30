package cultureapp.rest.cultural_offer;


import cultureapp.domain.cultural_offer.command.AddCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.command.DeleteCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/cultural-offers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CulturalOfferController {
    private final AddCulturalOfferUseCase addCulturalOfferUseCase;
    private final DeleteCulturalOfferUseCase deleteCulturalOfferUseCase;

    @PostMapping("")
    public void addCulturalOffer(@RequestBody CulturalOfferRequest request)
            throws IOException, SubcategoryNotFoundException {
        AddCulturalOfferUseCase.AddCulturalOfferCommand command
            = new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                    request.getName(),
                    request.getLongitude(),
                    request.getLatitude(),
                    null,
                    request.getDescription(),
                    request.getCategoryId(),
                    request.getSubcategoryId());

        addCulturalOfferUseCase.addCulturalOffer(command);
    }

    @DeleteMapping("/{id}")
    public void deleteCulturalOffer(@PathVariable Long id) throws CulturalOfferNotFoundException {
        deleteCulturalOfferUseCase.deleteCulturalOffer(id);
    }

    // Stream ne moze da baci gresku???
    private List<byte[]> readBytes(List<MultipartFile> files) throws IOException {
        List<byte[]> bytes = new ArrayList<>();
        for (MultipartFile file : files) {
            bytes.add(file.getBytes());
        }
        return bytes;
    }
}
