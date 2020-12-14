package cultureapp.rest.subscription;


import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQuery;
import cultureapp.domain.regular_user.exception.RegularUserNotFound;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<GetSubscriptionsForUserQuery.GetSubscriptionsForUserDTO>> getSubscriptions(
            @RequestParam(name = "categoryId") Long categoryId,
            @RequestParam(name = "subcategoryId") Long subcategoryId)
            throws SubcategoryNotFoundException, RegularUserNotFound {
        return ResponseEntity.ok(getSubscriptionsForUserQuery.getSubscriptions(categoryId, subcategoryId));
    }
}
