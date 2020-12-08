package cultureapp.infrastructure.datetime;

import cultureapp.domain.date_time.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateTimeProviderService implements DateTimeProvider {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}