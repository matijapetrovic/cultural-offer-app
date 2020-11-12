package cultureapp.domain.core.example;

import cultureapp.domain.core.example.commands.AddExampleUseCase;
import cultureapp.domain.core.example.commands.DeleteExampleByIdUseCase;
import cultureapp.domain.core.example.commands.UpdateExampleUseCase;
import cultureapp.domain.core.example.exceptions.ExampleNotFoundException;
import cultureapp.domain.core.example.queries.GetExampleByIdQuery;
import cultureapp.domain.core.example.queries.GetExamplesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
class ExampleService implements
        AddExampleUseCase,
        GetExamplesQuery,
        GetExampleByIdQuery,
        DeleteExampleByIdUseCase,
        UpdateExampleUseCase {
    private final ExampleRepository repository;

    @Override
    public void addExample(AddExampleCommand command) {
        Example example = Example.of(command.getEmail(), command.getNumber());
        repository.save(example);
    }

    @Override
    public List<GetExamplesQuery.ExampleDTO> getExamples() {
        return repository
                .findAll()
                .stream()
                .map(GetExamplesQuery.ExampleDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public GetExampleByIdQuery.ExampleDTO getExample(Long id) {
        Example example = repository.findById(id).orElseThrow();
        return GetExampleByIdQuery.ExampleDTO.of(example);
    }

    @Override
    public void deleteExampleById(Long id) throws ExampleNotFoundException {
        Optional<Example> example = repository.findById(id);
        if (example.isEmpty())
            throw new ExampleNotFoundException(String.format("Example with id %d doesn't exist.", id));
        repository.delete(example.get());
    }

    @Override
    public void updateExample(UpdateExampleCommand command) throws ExampleNotFoundException {
        if (!repository.existsById(command.getId()))
            throw new ExampleNotFoundException(String.format("Example with id %d doesn't exist.", command.getId()));
        Example example = Example.withId(command.getId(), command.getEmail(), command.getNumber());
        repository.save(example);
    }
}
