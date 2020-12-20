package cultureapp.rest.subscription;


import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQuery;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {
    private final GetSubscriptionsForUserQuery getSubscriptionsForUserQuery;

    @GetMapping(value = "", params = {"categoryId", "subcategoryId"})
    @PreAuthorize(value="hasRole('ROLE_USER')")
    public ResponseEntity<List<GetSubscriptionsForUserQuery.GetSubscriptionsForUserDTO>> getSubscriptions(
            @RequestParam(name = "categoryId") Long categoryId,
            @RequestParam(name = "subcategoryId") Long subcategoryId)
            throws SubcategoryNotFoundException, RegularUserNotFoundException {
        return ResponseEntity.ok(getSubscriptionsForUserQuery.getSubscriptions(categoryId, subcategoryId));
    }


}
