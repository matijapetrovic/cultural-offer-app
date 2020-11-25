package cultureapp.rest.news;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/cultural-offer/{culturalOfferId}/news")
public class NewsController {
}
