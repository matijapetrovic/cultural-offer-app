package cultureapp.rest;

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

    public static <T> PaginatedResponse<T> of(Slice<T> data, UriComponentsBuilder uriBuilder, String resourceUri) {
        PaginatedResponse<T> response = new PaginatedResponse<T>(data.getContent());

        response.addLink("self",
                constructUriPage(uriBuilder, resourceUri, data.getNumber() + 1, data.getSize()));
        if (data.hasNext())
            response.addLink("next",
                    constructUriPage(uriBuilder, resourceUri, data.getNumber() + 1, data.getSize()));
        if (data.hasPrevious())
            response.addLink("prev",
                    constructUriPage(uriBuilder, resourceUri, data.getNumber() - 1, data.getSize()));
        return response;
    }

    private void addLink(String rel, String link) {
        links.put(rel, link);
    }

    private static String constructUriPage(UriComponentsBuilder uriBuilder, String resourceUri, int page, int size) {
        uriBuilder.path(resourceUri);
        return uriBuilder.replaceQueryParam("page", page)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }
}
