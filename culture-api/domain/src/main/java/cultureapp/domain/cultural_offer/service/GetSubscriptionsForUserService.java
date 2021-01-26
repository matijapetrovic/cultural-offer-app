package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.account.Account;
import cultureapp.domain.core.AuthenticationService;
import cultureapp.domain.cultural_offer.query.GetSubscriptionsForUserQueryHandler;
import cultureapp.domain.user.RegularUser;
import cultureapp.domain.user.RegularUserRepository;
import cultureapp.domain.user.exception.RegularUserNotFoundException;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.subcategory.SubcategoryId;
import cultureapp.domain.subcategory.SubcategoryRepository;
import cultureapp.domain.subcategory.exception.SubcategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetSubscriptionsForUserService implements GetSubscriptionsForUserQueryHandler {
    private final AuthenticationService authenticationService;
    private final RegularUserRepository regularUserRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public Slice<GetSubscriptionsForUserDTO> handleGetSubscriptions(GetSubscriptionsForUserQuery query) throws
            RegularUserNotFoundException,
            SubcategoryNotFoundException {
        Account authenticated = authenticationService.getAuthenticated();
        RegularUser user = regularUserRepository.findByAccountIdWithSubscriptions(authenticated.getId())
                .orElseThrow(() -> new RegularUserNotFoundException("Zasto email?"));

        Subcategory subcategory = subcategoryRepository.findById(SubcategoryId.of(query.getCategoryId(), query.getSubcategoryId()))
                .orElseThrow(() -> new SubcategoryNotFoundException(query.getSubcategoryId(), query.getCategoryId()));

        List<GetSubscriptionsForUserDTO> subscriptions = user.getCulturalOffers()
                .stream()
                .filter(culturalOffer -> culturalOffer.getSubcategory().equals(subcategory))
                .map(GetSubscriptionsForUserDTO::of)
                .collect(Collectors.toList());

        // PagedListHolder na svoju ruku menja page
        PagedListHolder<GetSubscriptionsForUserDTO> pagedListHolder =
                new PagedListHolder<>(
                        subscriptions,
                        new MutableSortDefinition(
                                "name",
                                false,
                                true
                        )
                );
        pagedListHolder.resort();
        pagedListHolder.setPage(query.getPage().intValue());
        pagedListHolder.setPageSize(query.getLimit().intValue());

        List<GetSubscriptionsForUserDTO> pageList = pagedListHolder.getPageList();
        int nrOfElements = pagedListHolder.getNrOfElements();

        Pageable pageRequest = PageRequest.of(query.getPage().intValue(), query.getLimit().intValue(), Sort.by("name"));


        if (isPageChanged(query.getPage().intValue(), pagedListHolder.getPage()))
            return new PageImpl<>(List.of(), pageRequest, 0);
        else
            return new PageImpl<>(pageList, pageRequest, nrOfElements);
    }

    private boolean isPageChanged(int queryPage, int pagedListHolderPage) {
        return queryPage != pagedListHolderPage;
    }
}
