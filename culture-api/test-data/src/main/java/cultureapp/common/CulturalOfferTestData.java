package cultureapp.common;

import cultureapp.domain.cultural_offer.CulturalOffer;
import cultureapp.domain.cultural_offer.Location;
import cultureapp.domain.cultural_offer.command.SubscribeToCulturalOfferNewsUseCase;
import cultureapp.domain.cultural_offer.command.UnsubscribeFromCulturalOfferNewsUseCase;
import cultureapp.domain.subcategory.Subcategory;

import java.util.ArrayList;
import java.util.HashSet;

public class CulturalOfferTestData {
    public static Long EXISTING_CULTURAL_OFFER_ID = 1L;
    public static Long NON_EXISTING_CULTURAL_OFFER_ID = 5L;

    public static long NUMBER_OF_CULTURAL_OFFERS_FOR_SUBCATEGORY_1_1 = 2;
    public static long NUMBER_OF_CULTURAL_OFFERS_FOR_SUBCATEGORY_1_2 = 1;


    public static long NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1_AND_SUBCATEGORY_1_1 = 1;
    public static int NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1 = 2;
    public static Long NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1 = 2L;
    public static Long EXISTING_SUBSCRIPTION_ID_FOR_USER_1 = 1L;

    public static int NUMBER_OF_SUBSCRIPTIONS_FOR_USER_2 = 1;
    public static Long NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_2= 1L;
    public static Long EXISTING_SUBSCRIPTION_ID_FOR_USER_2 = 2L;


    public static Long INVALID_CULTURAL_OFFER_ID = -1L;
    public static Long VALID_CULTURAL_OFFER_ID = 1L;
    public static String VALID_CULTURAL_OFFER_NAME = "CulturalOffer1";
    public static String VALID_CULTURAL_OFFER_DESCRIPTION = "Description1";
    public static double VALID_LOCATION_LATITUDE = 45.0;
    public static double VALID_LOCATION_LONGITUDE = 45.0;
    public static String VALID_LOCATION_ADDRESS = "Address 1";
    public static Location VALID_CULTURAL_OFFER_LOCATION = Location.of(
            VALID_LOCATION_LONGITUDE,
            VALID_LOCATION_LATITUDE,
            VALID_LOCATION_ADDRESS);

    public static CulturalOffer validCulturalOfferWithSubcategory(Subcategory subcategory) {
        return CulturalOffer.withId(
                VALID_CULTURAL_OFFER_ID,
                VALID_CULTURAL_OFFER_NAME,
                VALID_CULTURAL_OFFER_DESCRIPTION,
                VALID_CULTURAL_OFFER_LOCATION,
                new ArrayList<>(),
                new HashSet <>(),
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
