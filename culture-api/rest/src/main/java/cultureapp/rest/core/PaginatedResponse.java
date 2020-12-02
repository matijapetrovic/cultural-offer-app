package cultureapp.rest.core;

import lombok.Getter;
import org.springframework.data.domain.Slice;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class PaginatedResponse<T> {
    private final List<T> data;
    private final Map<String, String> links;

    private PaginatedResponse(List<T> data) {
        this.data = data;
        this.links = new HashMap<>();
    }

    public static <T> PaginatedResponse<T> of(Slice<T> data, UriComponentsBuilder uriBuilder) {
        PaginatedResponse<T> response = new PaginatedResponse<T>(data.getContent());

        response.addLink("self",
                constructUriPage(uriBuilder, data.getNumber(), data.getSize()));
        if (data.hasNext())
            response.addLink("next",
                    constructUriPage(uriBuilder, data.getNumber() + 1, data.getSize()));
        if (data.hasPrevious())
            response.addLink("prev",
                    constructUriPage(uriBuilder, data.getNumber() - 1, data.getSize()));
        return response;
    }

    private void addLink(String rel, String link) {
        links.put(rel, link);
    }

    private static String constructUriPage(UriComponentsBuilder uriBuilder, int page, int limit) {
        return uriBuilder.replaceQueryParam("page", page)
                .replaceQueryParam("limit", limit)
                .build()
                .encode()
                .toUriString();
    }
}
