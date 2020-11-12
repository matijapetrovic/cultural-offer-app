package cultureapp.domain.core.example.queries;

import cultureapp.domain.core.example.Example;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;

public interface GetExamplesQuery {
    List<ExampleDTO> getExamples();

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    class ExampleDTO {
        Long id;
        String email;
        Integer number;

        public static ExampleDTO of(Example example) {
            return new ExampleDTO(
                    example.getId(),
                    example.getEmail(),
                    example.getNumber());
        }
    }
}
