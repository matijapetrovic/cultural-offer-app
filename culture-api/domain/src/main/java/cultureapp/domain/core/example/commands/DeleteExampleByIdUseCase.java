package cultureapp.domain.core.example.commands;

import cultureapp.domain.core.example.exceptions.ExampleNotFoundException;

import javax.validation.constraints.NotNull;

public interface DeleteExampleByIdUseCase {
    void deleteExampleById(@NotNull Long id) throws ExampleNotFoundException;
}
