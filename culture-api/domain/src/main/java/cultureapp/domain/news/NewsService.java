package cultureapp.domain.news;

import cultureapp.domain.core.EmailSender;
import cultureapp.domain.user.Administrator;
import cultureapp.domain.user.AdministratorRepository;
import cultureapp.domain.user.RegularUser;
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
import cultureapp.domain.news.query.GetNewsByIdQueryHandler;
import cultureapp.domain.news.query.GetNewsForOfferQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NewsService implements
        AddNewsUseCase,
        DeleteNewsUseCase,
        UpdateNewsUseCase,
        GetNewsForOfferQueryHandler,
        GetNewsByIdQueryHandler {
    private final NewsRepository newsRepository;
    private final CulturalOfferRepository culturalOfferRepository;
    private final AdministratorRepository administratorRepository;
    private final ImageRepository imageRepository;
    private final EmailSender emailSender;

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

        newsRepository.save(news);
        notifySubscribers(offer);
    }

    @Override
    public void deleteNews(@Positive Long culturalOfferId, @Positive Long id) throws NewsNotFoundException, CulturalOfferNotFoundException {
        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(culturalOfferId)
                .orElseThrow(() -> new CulturalOfferNotFoundException(culturalOfferId));

        News news = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(id, culturalOfferId)
                .orElseThrow(() -> new NewsNotFoundException(id, culturalOfferId));

        news.setArchived(true);

        newsRepository.save(news);
        notifySubscribers(offer);
    }

    // TODO autor ove metode da je procita par puta i obrati paznju na culturalOfferId i update pomocu njega :)
    // TODO: autor ove metoje ju je pročitao jednom i obratio pažnju na culturalOfferId i update pomoću njega ;)
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
        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(command.getCulturalOfferID())
                .orElseThrow(() -> new CulturalOfferNotFoundException(command.getCulturalOfferID()));
//        if (updateCulturalOffer(news, command.getCulturalOfferID())) {
//            news.setCulturalOffer(offer);
//        }

        if (updateAuthor(news, command.getAuthorID())) {
            Administrator admin = administratorRepository.findById(command.getAuthorID())
                    .orElseThrow(() -> new AdminNotFoundException(command.getAuthorID()));
            news.setAuthor(admin);
        }

        newsRepository.save(news);
        notifySubscribers(offer);
    }

    private boolean updateCulturalOffer(News news, Long culturalOfferId) {
        return !news.getCulturalOffer().getId().equals(culturalOfferId);
    }

    private boolean updateAuthor(News news, Long adminId) {
        return !news.getAuthor().getId().equals(adminId);
    }

    public Slice<GetNewsForOfferDTO> handleGetNewsForOffer(GetNewsForOfferQuery query) throws CulturalOfferNotFoundException {

        CulturalOffer offer = culturalOfferRepository.findByIdAndArchivedFalse(query.getCulturalOfferId())
                .orElseThrow(() -> new CulturalOfferNotFoundException(query.getCulturalOfferId()));

        Pageable pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by("title"));

        Slice<News> news = newsRepository.findAllByCulturalOfferIdAndArchivedFalse(query.getCulturalOfferId(), pageRequest);

        return news.map(GetNewsForOfferDTO::of);

    }

    @Override
    public GetNewsByIdDTO handleGetNewsById(GetNewsByIdQuery query) throws NewsNotFoundException {
        News news = newsRepository.findByIdAndCulturalOfferIdAndArchivedFalse(query.getId(), query.getCulturalOfferId())
                .orElseThrow(() -> new NewsNotFoundException(query.getId(), query.getCulturalOfferId()));

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

    private void notifySubscribers(CulturalOffer culturalOffer) {
        emailSender.notifySubscribers(
                mapSubscribers(culturalOffer.getSubscribers()),
                culturalOffer.getId());
    }

    private List<EmailSender.SubscriberDTO> mapSubscribers(Set<RegularUser> users) {
        return users.stream()
                .map(EmailSender.SubscriberDTO::of)
                .collect(Collectors.toList());
    }
}
