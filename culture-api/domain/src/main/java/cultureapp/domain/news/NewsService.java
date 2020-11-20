package cultureapp.domain.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class NewsService {
    private final NewsRepository newsRepository;
}
