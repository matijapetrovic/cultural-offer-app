package cultureapp.rest.regularUser;

import cultureapp.domain.regular_user.RegularUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/regular-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegularUserController {
    private final RegularUserService regularUserService;

    @Autowired
    public RegularUserController(RegularUserService regularUserService) {
        this.regularUserService = regularUserService;
    }
}
