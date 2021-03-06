package cultureapp.common;

import java.math.BigDecimal;

public class CulturalOfferTestData {
    public static final Double VALID_CULTURAL_OFFER_LONGITUDE = 1.0;
    public static final Double VALID_CULTURAL_OFFER_LATITUDE = 1.0;
    public static final String VALID_CULTURAL_OFFER_ADDRESS = "Non-empty-string";

    public static final Double INVALID_CULTURAL_OFFER_LONGITUDE = 400.0;
    public static final Double INVALID_CULTURAL_OFFER_LATITUDE = 400.0;
    public static final String INVALID_CULTURAL_OFFER_DESCRIPTION = "";

    public static Long EXISTING_CULTURAL_OFFER_ID = 1L;
    public static Long EXISTING_CULTURAL_OFFER_ID_2 = 2L;
    public static Long NON_EXISTING_CULTURAL_OFFER_ID = 5L;
    public static final String EXISTING_CULTURAL_OFFER_NAME = "CulturalOffer1";
    public static final String NON_EXISTING_CULTURAL_OFFER_NAME = "CulturalOffer5";

    public static final long NUMBER_OF_CULTURAL_OFFERS_FOR_SUBCATEGORY_1_1 = 2;
    public static final long NUMBER_OF_CULTURAL_OFFERS_FOR_SUBCATEGORY_1_2 = 1;

    public static final long NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1_AND_SUBCATEGORY_1_1 = 1;
    public static final int NUMBER_OF_SUBSCRIPTIONS_FOR_USER_1 = 2;
    public static final Long NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_1 = 2L;
    public static final Long EXISTING_SUBSCRIPTION_ID_FOR_USER_1 = 1L;

    public static final int EXISTING_CULTURAL_OFFERS_FOR_SUBCATEGORY_1_1 = 2;

    public static final Double EXISTING_LATITUDE_RANGE_FROM = 14.0;
    public static final Double EXISTING_LATITUDE_RANGE_TO = 31.0;
    public static final Double EXISTING_LONGITUDE_RANGE_FROM = 14.0;
    public static final Double EXISTING_LONGITUDE_RANGE_TO = 31.0;

    public static final int NUMBER_OF_SUBSCRIPTIONS_FOR_USER_2 = 1;
    public static final Long NON_EXISTING_SUBSCRIPTION_ID_FOR_USER_2= 1L;
    public static final Long EXISTING_SUBSCRIPTION_ID_FOR_USER_2 = 2L;

    public static final Long INVALID_CULTURAL_OFFER_ID = -1L;
    public static final String INVALID_CULTURAL_OFFER_NAME = "";
    public static final Long VALID_CULTURAL_OFFER_ID = 5L;
    public static final String VALID_CULTURAL_OFFER_NAME = "Non-empty-string";
    public static final String VALID_CULTURAL_OFFER_DESCRIPTION = "Description1";
    public static final double VALID_LOCATION_LATITUDE = 45.0;
    public static final double VALID_LOCATION_LONGITUDE = 45.0;
    public static final String VALID_LOCATION_ADDRESS = "Address 1";
    public static final BigDecimal VALID_CULTURAL_OFFER_RATING = BigDecimal.valueOf(3.0);
    public static final int VALID_CULTURAL_OFFER_REVIEW_COUNT = 1;


    public static final Double VALID_LATITUDE_RANGE_FROM = -15.0;
    public static final Double VALID_LATITUDE_RANGE_TO = 15.0;
    public static final Double VALID_LONGITUDE_RANGE_FROM = -15.0;
    public static final Double VALID_LONGITUDE_RANGE_TO = 15.0;
    public static final Long VALID_FILTER_CATEGORY_ID = 1L;
    public static final Long VALID_FILTER_SUBCATEGORY_ID = 1L;

    public static final Double INVALID_LATITUDE_RANGE_FROM = 30.0;
    public static final Double INVALID_LATITUDE_RANGE_TO = 20.0;
    public static final Double INVALID_LONGITUDE_RANGE_FROM = 30.0;
    public static final Double INVALID_LONGITUDE_RANGE_TO = 30.0;

    public static final Long INVALID_FILTER_CATEGORY_ID = null;
    public static final Long INVALID_FILTER_SUBCATEGORY_ID = 1L;

    public static final Integer VALID_PAGE_NUMBER = 0;
    public static final Integer VALID_LIMIT_NUMBER = 2;
    public static final Integer INVALID_PAGE_NUMBER = -1;
    public static final Integer INVALID_LIMIT_NUMBER = 0;


    public static final int FIRST_PAGE_CULTURAL_OFFERS = 0;
    public static final int LAST_PAGE_CULTURAL_OFFERS = 1;
    public static final int CULTURAL_OFFER_PAGE_SIZE = 2;

    public static final int FIRST_PAGE_NUM_CULTURAL_OFFERS = 2;
    public static final int LAST_PAGE_NUM_CULTURAL_OFFERS = 1;

    public static final Long EXISTING_ADD_CULTURAL_OFFER_IMAGE_1 = 3L;
    public static final Long EXISTING_ADD_CULTURAL_OFFER_IMAGE_2 = 4L;

    public static final Long EXISTING_UPDATE_CULTURAL_OFFER_IMAGE_1 = 5L;
    public static final Long EXISTING_UPDATE_CULTURAL_OFFER_IMAGE_2 = 6L;

}
