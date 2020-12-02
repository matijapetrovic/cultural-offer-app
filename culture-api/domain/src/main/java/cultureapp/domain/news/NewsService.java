package cultureapp.domain.news;

import cultureapp.domain.administrator.Administrator;
import cultureapp.domain.administrator.AdministratorRepository;
import cultureapp.domain.administrator.exception.AdminNotFoundException;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.Image;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.news.command.AddNewsUseCase;
import cultureapp.domain.news.command.DeleteNewsUseCase;
import cultureapp.domain.news.exception.NewsNotFoundException;
import cultureapp.domain.news.query.GetNewsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsService implements
        AddNewsUseCase,
        DeleteNewsUseCase,
        GetNewsQuery {
    private final NewsRepository newsRepository;
    private final CulturalOfferRepository culturalOfferRepository;
    private final AdministratorRepository administratorRepository;

    @Override
    public void addNews(AddNewsCommand command) throws CulturalOfferNotFoundException, AdminNotFoundException {
        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferID())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferID()));

        Administrator admin = administratorRepository.findById(command.getAuthorID())
                .orElseThrow(() -> new AdminNotFoundException(command.getAuthorID()));

        News news = News.of(
                offer,
                command.getName(),
                command.getPostedDate(),
                admin,
                command.getText(),
                false,
                null
        );

        newsRepository.save(news);
    }

    @Override
    public void deleteNews(@Positive Long culturalOfferId, @Positive Long id) throws NewsNotFoundException {
        News news = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, culturalOfferId)
                .orElseThrow(() -> new NewsNotFoundException(id));
    }

    @Override
    public Slice<GetNewsDTO> getNews(Long offerId, Integer page, Integer limit) throws CulturalOfferNotFoundException {

        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(offerId)
                .orElseThrow(() -> new CulturalOfferNotFoundException(offerId));

        Pageable pageRequest = PageRequest.of(page, limit, Sort.by("name"));

        Slice<News> news = newsRepository.findAllByCulturalOfferIdAndArchivedFalse(offerId, pageRequest);

        return news.map(GetNewsDTO::of);

    }
}
