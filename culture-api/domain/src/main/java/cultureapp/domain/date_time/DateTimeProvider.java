package cultureapp.domain.date_time;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime now();
}
