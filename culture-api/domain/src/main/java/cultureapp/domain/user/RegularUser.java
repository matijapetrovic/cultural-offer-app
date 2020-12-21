package cultureapp.domain.user;


import cultureapp.domain.account.Account;
import cultureapp.domain.cultural_offer.CulturalOffer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "regular_user")
public class RegularUser extends User {

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },
    fetch = FetchType.LAZY)
    @JoinTable(name = "subscription",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private Set<CulturalOffer> culturalOffers;

    public boolean subscribe(CulturalOffer culturalOffer) {
        return culturalOffers.add(culturalOffer);
    }

    public boolean unsubscribe(CulturalOffer culturalOffer) {
        return culturalOffers.remove(culturalOffer);
    }

    public boolean isSubscribedTo(CulturalOffer culturalOffer) {
        return culturalOffers.stream().anyMatch(culturalOffer::equals);
    }

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
