package cultureapp.rest.news;


import cultureapp.domain.administrator.exception.AdminNotFoundException;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.image.exception.ImageNotFoundException;
import cultureapp.domain.news.command.AddNewsUseCase;
import cultureapp.domain.news.command.DeleteNewsUseCase;
import cultureapp.domain.news.command.UpdateNewsUseCase;
import cultureapp.domain.news.exception.NewsAlreadyExistException;
import cultureapp.domain.news.exception.NewsNotFoundException;
import cultureapp.domain.news.query.GetNewsByIdQuery;
import cultureapp.domain.news.query.GetNewsQuery;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/cultural-offers/{culturalOfferId}/news")
public class NewsController {
    private final AddNewsUseCase addNews;
    private final GetNewsQuery getNewsQuery;
    private final DeleteNewsUseCase deleteNews;
    private final GetNewsByIdQuery getNewsByIdQuery;
    private final UpdateNewsUseCase updateNews;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addNews(@PathVariable Long culturalOfferId, @RequestBody NewsRequest request) throws CulturalOfferNotFoundException, AdminNotFoundException, NewsAlreadyExistException, ImageNotFoundException {
        LocalDateTime now = LocalDateTime.now();

        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                culturalOfferId,
                request.getName(),
                now,
                request.getAuthorID(),
                request.getText(),
                request.getImages()
        );

        addNews.addNews(command);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteNews(@PathVariable Long culturalOfferId, @PathVariable Long id) throws NewsNotFoundException, CulturalOfferNotFoundException {
        deleteNews.deleteNews(culturalOfferId, id);
    }

    @GetMapping(value = "", params = { "page", "limit" })
    public ResponseEntity<PaginatedResponse<GetNewsQuery.GetNewsDTO>> getNews(
            @PathVariable Long culturalOfferId,
            @RequestParam(name="page", required = true) Integer page,
            @RequestParam(name="limit", required = true) Integer limit,
            UriComponentsBuilder uriBuilder
    ) throws CulturalOfferNotFoundException {
        Slice<GetNewsQuery.GetNewsDTO> result = getNewsQuery.getNews(culturalOfferId, page, limit);
        String uri = String.format("/api/cultural-offers/%d/news", culturalOfferId);
        uriBuilder.path(uri);
        return  ResponseEntity.ok(PaginatedResponse.of(result, uriBuilder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetNewsByIdQuery.GetNewsByIdDTO> getNewsById(
            @PathVariable Long culturalOfferId,
            @PathVariable Long id) throws NewsNotFoundException {
        return ResponseEntity.ok(getNewsByIdQuery.getNewsById(id, culturalOfferId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateNews(@PathVariable Long id,
                           @PathVariable Long culturalOfferId,
                           @RequestBody NewsRequest request) throws CulturalOfferNotFoundException, NewsNotFoundException, AdminNotFoundException, NewsAlreadyExistException, ImageNotFoundException {
        UpdateNewsUseCase.UpdateNewsCommand command = new UpdateNewsUseCase.UpdateNewsCommand(
                id,
                culturalOfferId,
                request.getName(),
                LocalDateTime.now(),
                request.getAuthorID(),
                request.getText(),
                request.getImages());

        updateNews.updateNews(command);
    }
}
