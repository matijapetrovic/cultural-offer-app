package cultureapp.common;

import cultureapp.domain.category.Category;
import cultureapp.domain.subcategory.Subcategory;

public class SubcategoryTestData {
    public static final String EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1 = "Subcategory11";
    public static final String EXISTING_SUBCATEGORY_NAME_2_FOR_CATEGORY_ID_1 = "Subcategory12";
    public static final String NON_EXISTING_SUBCATEGORY_NAME_FOR_CATEGORY_ID_1 = "Subcategory15";

    public static final Long EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1 = 1L;
    public static final Long NON_EXISTING_SUBCATEGORY_ID_FOR_CATEGORY_ID_1 = 5L;


    public static final int SUBCATEGORY_PAGE_SIZE = 2;
    public static final int FIRST_PAGE_FOR_CATEGORY_ID_1 = 0;
    public static final int LAST_PAGE_FOR_CATEGORY_ID_1 = 1;
    public static final long FIRST_PAGE_NUM_SUBCATEGORIES_FOR_CATEGORY_ID_1 = 2L;
    public static final long LAST_PAGE_NUM_SUBCATEGORIES_FOR_CATEGORY_ID_1 = 1L;

    public static final String INVALID_SUBCATEGORY_NAME = "";
    public static final Long INVALID_SUBCATEGORY_ID = -1L;


    public static final String VALID_SUBCATEGORY_NAME = "Non-empty-string";
    public static final String VALID_UPDATE_SUBCATEGORY_NAME = "New non-empty-string";
    public static final Long VALID_SUBCATEGORY_ID = 5L;

    public static Subcategory validSubcategoryWithCategory(Category category) {
        return Subcategory.withId(
                VALID_SUBCATEGORY_ID,
                category,
                VALID_SUBCATEGORY_NAME);
    }
}
