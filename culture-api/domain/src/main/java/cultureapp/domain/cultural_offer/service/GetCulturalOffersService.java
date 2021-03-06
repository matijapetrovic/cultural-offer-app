package cultureapp.domain.cultural_offer.service;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.query.GetCulturalOffersQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RequiredArgsConstructor
@Service
public class GetCulturalOffersService implements GetCulturalOffersQueryHandler {
    private final CulturalOfferRepository repository;

    @Override
    public Slice<GetCulturalOffersDTO> handleGetCulturalOffers(GetCulturalOffersQuery query) {
        Pageable pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by("name"));

        Slice<CulturalOffer> culturalOffers = repository.findAllByArchivedFalse(pageRequest);

        return culturalOffers.map(GetCulturalOffersDTO::of);
    }
}
