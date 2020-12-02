package cultureapp.rest.news;


import cultureapp.domain.administrator.exception.AdminNotFoundException;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.cultural_offer.query.GetCulturalOffersQuery;
import cultureapp.domain.news.NewsService;
import cultureapp.domain.news.command.AddNewsUseCase;
import cultureapp.domain.news.command.UpdateNewsUseCase;
import cultureapp.domain.news.command.DeleteNewsUseCase;
import cultureapp.domain.news.exception.NewsNotFoundException;
import cultureapp.domain.news.query.GetNewsByIdQuery;
import cultureapp.domain.news.query.GetNewsQuery;
import cultureapp.rest.core.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.websocket.server.PathParam;
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
    public void addNews(@PathVariable Long culturalOfferId, @RequestBody NewsRequest request) throws CulturalOfferNotFoundException, AdminNotFoundException {
        LocalDateTime now = LocalDateTime.now();

        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                culturalOfferId,
                request.getName(),
                now,
                request.getAuthorID(),
                request.getText(),
                null
        );

        addNews.addNews(command);
    }

    @DeleteMapping("/{id}")
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
    public void updateNews(@PathVariable Long id,
                           @PathVariable Long culturalOfferId,
                           @RequestBody NewsRequest request) throws CulturalOfferNotFoundException, NewsNotFoundException, AdminNotFoundException {
        UpdateNewsUseCase.UpdateNewsCommand command = new UpdateNewsUseCase.UpdateNewsCommand(
                id,
                culturalOfferId,
                request.getName(),
                LocalDateTime.now(),
                request.getAuthorID(),
                request.getText()
        );

        updateNews.updateNews(command);
    }
}
