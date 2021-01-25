package cultureapp.rest.subscription;


import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQueryHandler;
import cultureapp.domain.subcategory.query.GetSubscriptionsSubcategoriesForUserQueryHandler;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {
    private final GetSubscriptionsForUserQueryHandler getSubscriptionsForUserQueryHandler;
    private final GetSubscriptionsSubcategoriesForUserQueryHandler getSubscriptionsSubcategoriesForUserQueryHandler;

    @GetMapping(value = "", params = {"page", "limit", "categoryId", "subcategoryId"})
    @PreAuthorize(value="hasRole('ROLE_USER')")
    public ResponseEntity<PaginatedResponse<GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserDTO>> getSubscriptions(
            @RequestParam(name = "page") Long page,
            @RequestParam(name = "limit") Long limit,
            @RequestParam(name = "categoryId") Long categoryId,
            @RequestParam(name = "subcategoryId") Long subcategoryId,
            UriComponentsBuilder uriBuilder
    )
            throws SubcategoryNotFoundException, RegularUserNotFoundException {
        GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery query =
                new GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserQuery(categoryId, subcategoryId, page, limit);

        Slice<GetSubscriptionsForUserQueryHandler.GetSubscriptionsForUserDTO> result = getSubscriptionsForUserQueryHandler.handleGetSubscriptions(query);
        uriBuilder.path("/api/subscriptions");
        return ResponseEntity.ok(PaginatedResponse.of(result, uriBuilder));
    }

    @GetMapping(value = "/subcategories", params = {"page", "limit"})
    @PreAuthorize(value="hasRole('ROLE_USER')")
    public ResponseEntity<PaginatedResponse<GetSubscriptionsSubcategoriesForUserQueryHandler.GetSubscriptionsSubcategoriesForUserDTO>>
            getSubscribedSubcategoriesForUser(
            @RequestParam(name = "page", required = true) Integer page,
            @RequestParam(name = "limit", required = true) Integer limit,
            UriComponentsBuilder uriBuilder
    ) throws RegularUserNotFoundException {
        GetSubscriptionsSubcategoriesForUserQueryHandler.GetSubscriptionsSubcategoriesForUserQuery
                query =
                new GetSubscriptionsSubcategoriesForUserQueryHandler.GetSubscriptionsSubcategoriesForUserQuery(
                        page, limit
                );
        Slice<GetSubscriptionsSubcategoriesForUserQueryHandler.GetSubscriptionsSubcategoriesForUserDTO>
                result = getSubscriptionsSubcategoriesForUserQueryHandler.getSubscribedSubcategoriesForUser(query);
        uriBuilder.path("/api/subscriptions/subcategories");
        return ResponseEntity.ok(PaginatedResponse.of(result, uriBuilder));
    }
}
