package cultureapp.domain.regular_user;


import cultureapp.domain.account.Account;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "regular_user")
public class RegularUser extends User {

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "subscription",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private Set<CulturalOffer> culturalOffers;

    private RegularUser(
            Long id,
            String firstName,
            String lastName,
            Account account,
            Set<CulturalOffer> culturalOffers
    ) {
        super(id, firstName, lastName, account);
        this.culturalOffers = culturalOffers;
    }

    public static RegularUser withId(
            Long id,
            String firstName,
            String lastName,
            Account account,
            Set<CulturalOffer> culturalOffers
    ) {
        return new RegularUser(
                id,
                firstName,
                lastName,
                account,
                culturalOffers
        );
    }

    public static RegularUser of(
            String firstName,
            String lastName,
            Account account
    ) {
        return withId(null, firstName, lastName, account, null);
    }
}