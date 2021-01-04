package cultureapp.domain.core;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime now();
}
