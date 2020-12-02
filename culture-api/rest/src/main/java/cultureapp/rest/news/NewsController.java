package cultureapp.rest.news;


import cultureapp.domain.administrator.exception.AdminNotFoundException;
import cultureapp.domain.cultural_offer.exception.CulturalOfferNotFoundException;
import cultureapp.domain.news.NewsService;
import cultureapp.domain.news.command.AddNewsUseCase;
import cultureapp.domain.news.exception.NewsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/cultural-offers/{culturalOfferId}/news")
public class NewsController {
    private final NewsService newsService;

    @PostMapping("")
    public void addNews(@RequestBody NewsRequest request) throws CulturalOfferNotFoundException, AdminNotFoundException {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toString());

        System.out.println(request.getName());

        AddNewsUseCase.AddNewsCommand command = new AddNewsUseCase.AddNewsCommand(
                request.getCulturalOfferID(),
                request.getName(),
                request.getPostedDate(),
                request.getAuthorID(),
                request.getText(),
                null
        );

        newsService.addNews(command);
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long culturalOfferId, @PathVariable Long id) throws NewsNotFoundException {
        newsService.deleteNews(culturalOfferId, id);
    }
}
