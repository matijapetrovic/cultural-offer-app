package cultureapp.domain;

import static cultureapp.common.CulturalOfferTestData.*;
import static cultureapp.common.CategoryTestData.*;
import static cultureapp.common.SubcategoryTestData.*;
import static cultureapp.common.RegularUserTestData.*;

import cultureapp.domain.account.Account;
import cultureapp.domain.authority.Authority;
import cultureapp.domain.category.Category;
import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.command.SubscribeToCulturalOfferNewsUseCase;
import cultureapp.domain.cultural_offer.command.UnsubscribeFromCulturalOfferNewsUseCase;
import cultureapp.domain.subcategory.Subcategory;
import cultureapp.domain.user.RegularUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DomainUnitTestsUtil {

    public static Subcategory validSubcategoryWithCategory(Category category) {
        return Subcategory.withId(
                VALID_SUBCATEGORY_ID,
                category,
                VALID_SUBCATEGORY_NAME);
    }

    public static Category validCategory() {
        return Category.withId(
                VALID_CATEGORY_ID,
                VALID_CATEGORY_NAME);
    }


    public static Account validRegularUserAccount() {
        return Account.withId(
                VALID_REGULAR_USER_ACCOUNT_ID,
                VALID_REGULAR_USER_ACCOUNT_EMAIL,
                VALID_REGULAR_USER_ACCOUNT_PASSWORD,
                true,
                Set.of(Authority.of("ROLE_USER")));
    }

    public static RegularUser validRegularUserWithAccount(Account account) {
        return RegularUser.withId(
                VALID_REGULAR_USER_ID,
                VALID_REGULAR_USER_FIRST_NAME,
                VALID_REGULAR_USER_LAST_NAME,
                account,
                new HashSet<>());
    }


    public static Location VALID_CULTURAL_OFFER_LOCATION = Location.of(
            VALID_LOCATION_LONGITUDE,
            VALID_LOCATION_LATITUDE,
            VALID_LOCATION_ADDRESS);

    public static CulturalOffer validCulturalOfferWithSubcategory(Subcategory subcategory) {
        return CulturalOffer.withId(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_RATING,
                VALID_CULTURAL_OFFER_REVIEW_COUNT,
                VALID_CULTURAL_OFFER_LOCATION,
                new ArrayList<>(),
                new HashSet<>(),
                subcategory);
    }

    public static SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand invalidSubscribeCommand() {
        return new SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand(
                INVALID_CULTURAL_OFFER_ID);
    }

    public static SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand validSubscribeCommand() {
        return new SubscribeToCulturalOfferNewsUseCase.SubscribeToCulturalOfferNewsCommand(
                VALID_CULTURAL_OFFER_ID);
    }

    public static UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand invalidUnsubscribeCommand() {
        return new UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand(
                INVALID_CULTURAL_OFFER_ID);
    }

    public static UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand validUnsubscribeCommand() {
        return new UnsubscribeFromCulturalOfferNewsUseCase.UnsubscribeFromCulturalOfferNewsCommand(
                VALID_CULTURAL_OFFER_ID);
    }
}
