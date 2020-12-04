package cultureapp.rest.regularUser;

import cultureapp.domain.account.exception.AccountAlreadyExists;
import cultureapp.domain.regular_user.RegularUserService;
import cultureapp.domain.regular_user.command.AddRegularUserUseCase;
import cultureapp.domain.regular_user.exception.RegularUserAlreadyExists;
import cultureapp.rest.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/regular-users", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegularUserController {
    private final RegularUserService regularUserService;

    @PostMapping("")
    public void addRegularUser(@RequestBody UserRequest request) throws AccountAlreadyExists {
        AddRegularUserUseCase.AddRegularUserCommand command =
                new AddRegularUserUseCase.AddRegularUserCommand(
                        request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword());
        regularUserService.addRegularUser(command);
    }
}
