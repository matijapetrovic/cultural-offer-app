package cultureapp.rest.example;

import cultureapp.domain.core.example.commands.AddExampleUseCase;
import cultureapp.domain.core.example.commands.DeleteExampleByIdUseCase;
import cultureapp.domain.core.example.commands.UpdateExampleUseCase;
import cultureapp.domain.core.example.exceptions.ExampleNotFoundException;
import cultureapp.domain.core.example.queries.GetExampleByIdQuery;
import cultureapp.domain.core.example.queries.GetExamplesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Component
@RequestMapping(value="/api/example", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ExampleController {
    private final AddExampleUseCase addExampleUseCase;
    private final DeleteExampleByIdUseCase deleteExampleByIdUseCase;
    private final UpdateExampleUseCase updateExampleUseCase;
    private final GetExamplesQuery getExamplesQuery;
    private final GetExampleByIdQuery getExampleByIdQuery;

    @GetMapping("")
    public ResponseEntity<List<GetExamplesQuery.ExampleDTO>> getExamples() {
        return ResponseEntity.ok(getExamplesQuery.getExamples());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetExampleByIdQuery.ExampleDTO> getExample(@PathVariable Long id) {
        return ResponseEntity.ok(getExampleByIdQuery.getExample(id));
    }

    @PostMapping("")
    public void addExample(@RequestBody ExampleRequest request) {
        AddExampleUseCase.AddExampleCommand command = new AddExampleUseCase.AddExampleCommand(
                request.getEmail(),
                request.getNumber());
        addExampleUseCase.addExample(command);
    }

    @DeleteMapping("/{id}")
    public void deleteExample(@PathVariable Long id) throws ExampleNotFoundException {
        deleteExampleByIdUseCase.deleteExampleById(id);
    }

    @PutMapping("/{id}")
    public void updateExample(@PathVariable Long id, @RequestBody ExampleRequest request)
            throws ExampleNotFoundException {
        UpdateExampleUseCase.UpdateExampleCommand command = new UpdateExampleUseCase.UpdateExampleCommand(
                id,
                request.getEmail(),
                request.getNumber());
        updateExampleUseCase.updateExample(command);
    }
}
