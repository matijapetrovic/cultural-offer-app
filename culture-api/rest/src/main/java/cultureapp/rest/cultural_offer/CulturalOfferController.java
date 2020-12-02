package cultureapp.rest.cultural_offer;


import cultureapp.domain.cultural_offer.command.AddCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.command.DeleteCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.command.UpdateCulturalOfferUseCase;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferByIdQuery;
import cultureapp.domain.cultural_offer.query.GetCulturalOffersQuery;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/cultural-offers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CulturalOfferController {
    private final AddCulturalOfferUseCase addCulturalOfferUseCase;
    private final DeleteCulturalOfferUseCase deleteCulturalOfferUseCase;
    private final UpdateCulturalOfferUseCase updateCulturalOfferUseCase;
    private final GetCulturalOffersQuery getCulturalOffersQuery;
    private final GetCulturalOfferByIdQuery getCulturalOfferByIdQuery;

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
    public void deleteCulturalOffer(@PathVariable(required = true) Long id) throws CulturalOfferNotFoundException {
        deleteCulturalOfferUseCase.deleteCulturalOffer(id);
    }

    @PutMapping("/{id}")
    public void updateCulturalOffer(@PathVariable(required = true) Long id,
                                    @RequestBody CulturalOfferRequest request)
            throws CulturalOfferNotFoundException, SubcategoryNotFoundException {
        UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand command = new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                id,
                request.getName(),
                request.getDescription(),
                request.getLongitude(),
                request.getLatitude(),
                request.getCategoryId(),
                request.getSubcategoryId()
        );

        updateCulturalOfferUseCase.updateCulturalOffer(command);
    }

    @GetMapping(value = "", params = { "page", "limit" })
    public ResponseEntity<PaginatedResponse<GetCulturalOffersQuery.GetCulturalOffersDTO>> getCulturalOffers(
            @RequestParam(name="page", required = true) Integer page,
            @RequestParam(name="limit", required = true) Integer limit,
            UriComponentsBuilder uriBuilder
    ) {
        Slice<GetCulturalOffersQuery.GetCulturalOffersDTO> result = getCulturalOffersQuery.getCulturalOffers(page, limit);
        uriBuilder.path("/api/cultural-offers");
        return  ResponseEntity.ok(PaginatedResponse.of(result, uriBuilder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCulturalOfferByIdQuery.GetCulturalOfferByIdDTO> getCulturalOffer(@PathVariable Long id)
            throws CulturalOfferNotFoundException {
        return ResponseEntity.ok(getCulturalOfferByIdQuery.getCulturalOffer(id));
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
