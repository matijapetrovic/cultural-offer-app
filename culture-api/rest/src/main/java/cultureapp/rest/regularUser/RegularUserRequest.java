package cultureapp.rest.regularUser;

import cultureapp.rest.user.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegularUserRequest extends UserRequest {
    // TODO: check if List<Long> culturalOfferIds is preferred (if not, make name unique)
    private List<String> culturalOffers;
}
