package cultureapp.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReviewTestData {
    public static final String EXISTING_REVIEW_NAME_FOR_CULTURAL_OFFER_ID_1 = "Subcategory11";
    public static final String EXISTING_REVIEW_NAME_2_FOR_CULTURAL_OFFER_ID_1 = "Subcategory12";
    public static final String NON_EXISTING_REVIEW_NAME_FOR_CULTURAL_OFFER_ID_1 = "Subcategory15";
    public static final BigDecimal EXISTING_REVIEW_RATING_FOR_REVIEW_WITH_ID_1 = BigDecimal.valueOf(4.34);

    public static final Long EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1 = 1L;
    public static final Long NON_EXISTING_REVIEW_ID_FOR_CULTURAL_OFFER_ID_1 = 5L;

    public static final int REVIEW_PAGE_SIZE = 2;
    public static final int FIRST_PAGE_FOR_CULTURAL_OFFER_ID_1 = 0;
    public static final int LAST_PAGE_FOR_CULTURAL_OFFER_ID_1 = 1;
    public static final long FIRST_PAGE_NUM_REVIEWS_FOR_CULTURAL_OFFER_ID_1 = 2L;
    public static final long LAST_PAGE_NUM_REVIEWS_FOR_CULTURAL_OFFER_ID_1 = 1L;

    public static final String INVALID_REVIEW_NAME = "";
    public static final Long INVALID_REVIEW_ID = -1L;

    public static final String VALID_REVIEW_NAME = "Non-empty-string";
    public static final String VALID_UPDATE_REVIEW_NAME = "New non-empty-string";
    public static final String VALID_REVIEW_COMMENT = "Non-empty-string";
    public static final BigDecimal VALID_REVIEW_RATING = BigDecimal.valueOf(5);
    public static final LocalDateTime VALID_REVIEW_DATE = LocalDateTime.of(2020, 3, 3,2,1);
    public static final Long VALID_REVIEW_ID = 5L;
}
