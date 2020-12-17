package cultureapp.domain.news;

import cultureapp.domain.user.Administrator;
import cultureapp.domain.user.AdministratorRepository;
import cultureapp.domain.user.exception.AdminNotFoundException;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.CulturalOfferRepository;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.Image;
import cultureapp.domain.image.ImageRepository;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.news.command.AddNewsUseCase;
import cultureapp.domain.news.command.DeleteNewsUseCase;
import cultureapp.domain.news.command.UpdateNewsUseCase;
import cultureapp.domain.news.exception.NewsAlreadyExistException;
import cultureapp.domain.news.exception.NewsNotFoundException;
import cultureapp.domain.news.query.GetNewsByIdQuery;
import cultureapp.domain.news.query.GetNewsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsService implements
        AddNewsUseCase,
        DeleteNewsUseCase,
        UpdateNewsUseCase,
        GetNewsQuery,
        GetNewsByIdQuery {
    private final NewsRepository newsRepository;
    private final CulturalOfferRepository culturalOfferRepository;
    private final AdministratorRepository administratorRepository;
    private final ImageRepository imageRepository;

    @Override
    public void addNews(AddNewsCommand command) throws CulturalOfferNotFoundException, AdminNotFoundException, NewsAlreadyExistException, ImageNotFoundException {
        List<Image> images = loadImages(command.getImages());

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
                images
        );


        // TODO: Implement sending mail to subscribed users

        newsRepository.save(news);
    }

    @Override
    public void deleteNews(@Positive Long culturalOfferId, @Positive Long id) throws NewsNotFoundException {
        News news = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, culturalOfferId)
                .orElseThrow(() -> new NewsNotFoundException(id, culturalOfferId));

        news.setArchived(true);

        newsRepository.save(news);
    }

    @Override
    public void updateNews(UpdateNewsCommand command)
            throws NewsNotFoundException,
            AdminNotFoundException,
            CulturalOfferNotFoundException,
            NewsAlreadyExistException, ImageNotFoundException {
        News news = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(command.getId(), command.getCulturalOfferID())
                .orElseThrow(() -> new NewsNotFoundException(command.getId(), command.getCulturalOfferID()));

        List<Image> images = loadImages(command.getImages());
        news.setImages(images);

        news.setTitle(command.getName());
        news.setPostedDate(command.getPostedDate());
        news.setText(command.getText());

        if (updateCulturalOffer(news, command.getCulturalOfferID())) {
            CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferID())
                    .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferID()));
            news.setCulturalOffer(offer);
        }

        if (updateAuthor(news, command.getAuthorID())) {
            Administrator admin = administratorRepository.findById(command.getAuthorID())
                    .orElseThrow(() -> new AdminNotFoundException(command.getAuthorID()));
            news.setAuthor(admin);
        }

        newsRepository.save(news);
    }

    private boolean updateCulturalOffer(News news, Long culturalOfferId) {
        return !news.getCulturalOffer().getId().equals(culturalOfferId);
    }

    private boolean updateAuthor(News news, Long adminId) {
        return !news.getAuthor().getId().equals(adminId);
    }

    @Override
    public Slice<GetNewsDTO> getNews(Long offerId, Integer page, Integer limit) throws CulturalOfferNotFoundException {

        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(offerId)
                .orElseThrow(() -> new CulturalOfferNotFoundException(offerId));

        Pageable pageRequest = PageRequest.of(page, limit, Sort.by("title"));

        Slice<News> news = newsRepository.findAllByCulturalOfferIdAndArchivedFalse(offerId, pageRequest);

        return news.map(GetNewsDTO::of);

    }

    @Override
    public GetNewsByIdDTO getNewsById(@Positive Long id, @Positive Long culturalOfferId) throws NewsNotFoundException {
        News news = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, culturalOfferId)
                .orElseThrow(() -> new NewsNotFoundException(id, culturalOfferId));

        return GetNewsByIdDTO.of(news);
    }


    private List<Image> loadImages(List<Long> imageIds) throws ImageNotFoundException {
        List<Image> images = new ArrayList<>();
        for (Long imageId : imageIds) {
            Image image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new ImageNotFoundException(imageId));
            images.add(image);
        }
        return images;
    }
}
