package cultureapp.rest.cultural_offer;


import cultureapp.domain.cultural_offer.command.*;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.exception.SubscriptionAlreadyExistsException;
import cultureapp.domain.cultural_offer.exception.SubscriptionNotFoundException;
import cultureapp.domain.cultural_offer.query.GetCulturalOfferByIdQueryHandler;
import cultureapp.domain.cultural_offer.query.GetCulturalOffersQueryHandler;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/cultural-offers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CulturalOfferController {
    private final SubscribeToCulturalOfferNewsUseCase subscribeToCulturalOfferNewsUseCase;
    private final UnsubscribeFromCulturalOfferNewsUseCase unsubscribeFromCulturalOfferNewsUseCase;

    private final AddCulturalOfferUseCase addCulturalOfferUseCase;
    private final DeleteCulturalOfferUseCase deleteCulturalOfferUseCase;
    private final UpdateCulturalOfferUseCase updateCulturalOfferUseCase;

    private final GetCulturalOffersQueryHandler getCulturalOffersQueryHandler;
    private final GetCulturalOfferByIdQueryHandler getCulturalOfferByIdQueryHandler;


    @PostMapping("/{id}/subscriptions")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> subscribe(@PathVariable Long id)
            throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionAlreadyExistsException {
        SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand command =
                new SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand(id);
        subscribeToCulturalOfferNewsUseCase.subscribe(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/subscriptions")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long id)
            throws CulturalOfferNotFoundException, RegularUserNotFoundException, SubscriptionNotFoundException {
        UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand command =
                new UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand(id);
        unsubscribeFromCulturalOfferNewsUseCase.unsubscribe(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCulturalOffer(@RequestBody CulturalOfferRequest request)
            throws SubcategoryNotFoundException, ImageNotFoundException {
        AddCulturalOfferUseCase.AddCulturalOfferCommand command
                = new AddCulturalOfferUseCase.AddCulturalOfferCommand(
                request.getName(),
                request.getLongitude(),
                request.getLatitude(),
                request.getImages(),
                request.getDescription(),
                request.getCategoryId(),
                request.getSubcategoryId());

        addCulturalOfferUseCase.addCulturalOffer(command);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCulturalOffer(@PathVariable(required = true) Long id) throws CulturalOfferNotFoundException {
        deleteCulturalOfferUseCase.deleteCulturalOffer(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCulturalOffer(@PathVariable(required = true) Long id,
                                    @RequestBody CulturalOfferRequest request)
            throws CulturalOfferNotFoundException, SubcategoryNotFoundException, ImageNotFoundException {
        UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand command = new UpdateCulturalOfferUseCase.UpdateCulturalOfferCommand(
                id,
                request.getName(),
                request.getDescription(),
                request.getLongitude(),
                request.getLatitude(),
                request.getCategoryId(),
                request.getSubcategoryId(),
                request.getImages());

        updateCulturalOfferUseCase.updateCulturalOffer(command);
    }

    @GetMapping(value = "", params = {"page", "limit"})
    public ResponseEntity<PaginatedResponse<GetCulturalOffersQueryHandler.GetCulturalOffersDTO>> getCulturalOffers(
            @RequestParam(name = "page", required = true) Integer page,
            @RequestParam(name = "limit", required = true) Integer limit,
            UriComponentsBuilder uriBuilder
    ) {
        GetCulturalOffersQueryHandler.GetCulturalOffersQuery query =
                new GetCulturalOffersQueryHandler.GetCulturalOffersQuery(page, limit);
        Slice<GetCulturalOffersQueryHandler.GetCulturalOffersDTO> result =
                getCulturalOffersQueryHandler.handleGetCulturalOffers(query);
        uriBuilder.path("/api/cultural-offers");
        return ResponseEntity.ok(PaginatedResponse.of(result, uriBuilder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdDTO> getCulturalOffer(@PathVariable Long id)
            throws CulturalOfferNotFoundException, RegularUserNotFoundException {
        GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery query =
                new GetCulturalOfferByIdQueryHandler.GetCulturalOfferByIdQuery(id);
        return ResponseEntity.ok(getCulturalOfferByIdQueryHandler.handleGetCulturalOffer(query));
    }
}

